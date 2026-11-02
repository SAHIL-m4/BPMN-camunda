package com.example.leave.service;

import com.example.leave.api.*;
import com.example.leave.domain.*;
import io.camunda.client.CamundaClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class LeaveService {
 private final LeaveRequestRepository requests;
 private final LeaveBalanceRepository balances;
 private final CamundaClient camunda;
 public LeaveService(LeaveRequestRepository requests, LeaveBalanceRepository balances, CamundaClient camunda) {
  this.requests=requests; this.balances=balances; this.camunda=camunda;
 }
 private int weekdays(LocalDate start, LocalDate end) {
  int count=0;
  for(LocalDate d=start; !d.isAfter(end); d=d.plusDays(1))
   if(d.getDayOfWeek()!=DayOfWeek.SATURDAY && d.getDayOfWeek()!=DayOfWeek.SUNDAY) count++;
  return count;
 }
 @Transactional
 public LeaveRequest submit(SubmitLeaveRequest body) {
  if(body.endDate().isBefore(body.startDate())) throw new IllegalArgumentException("End date must be on or after start date.");
  int days=weekdays(body.startDate(),body.endDate());
  LeaveRequest r=new LeaveRequest();
  r.setEmployeeName(body.employeeName()); r.setEmployeeEmail(body.employeeEmail());
  r.setLeaveType(body.leaveType()); r.setStartDate(body.startDate()); r.setEndDate(body.endDate());
  r.setDays(days); r.setReason(body.reason()); r.setStatus(LeaveStatus.SUBMITTED);
  requests.save(r);
  balances.findById(body.employeeEmail()).orElseGet(()->balances.save(new LeaveBalance(body.employeeEmail(),20)));
  Map<String,Object> vars=new HashMap<>();
  vars.put("requestId",r.getId()); vars.put("employeeEmail",r.getEmployeeEmail()); vars.put("leaveType",r.getLeaveType());
  vars.put("days",r.getDays()); vars.put("startDate",r.getStartDate().toString()); vars.put("endDate",r.getEndDate().toString());
  var response=camunda.newCreateInstanceCommand().bpmnProcessId("leave-request").latestVersion().variables(vars).send().join();
  r.setProcessInstanceKey(response.getProcessInstanceKey());
  return requests.save(r);
 }
 @Transactional
 public Map<String,Object> validate(Long id) {
  LeaveRequest r=requests.findById(id).orElseThrow();
  LeaveBalance b=balances.findById(r.getEmployeeEmail()).orElseGet(()->balances.save(new LeaveBalance(r.getEmployeeEmail(),20)));
  String message="OK";
  boolean valid=true;
  if(r.getDays()<=0) { valid=false; message="The selected period contains no working days."; }
  else if("ANNUAL".equalsIgnoreCase(r.getLeaveType()) && r.getDays()>b.getRemainingDays()) {
   valid=false; message="Insufficient annual leave balance.";
  }
  if(valid) r.setStatus(LeaveStatus.PENDING_APPROVAL);
  else { r.setStatus(LeaveStatus.REJECTED); r.setStatusMessage(message); }
  requests.save(r);
  return Map.of("valid",valid,"validationMessage",message);
 }
 @Transactional
 public void finalizeRequest(Long id, boolean approved, String comment, String validationMessage) {
  LeaveRequest r=requests.findById(id).orElseThrow();
  if(r.getStatus()==LeaveStatus.APPROVED || r.getStatus()==LeaveStatus.REJECTED) return;
  if(approved) {
   r.setStatus(LeaveStatus.APPROVED); r.setStatusMessage(comment == null ? "Approved" : comment);
   if("ANNUAL".equalsIgnoreCase(r.getLeaveType())) {
    LeaveBalance b=balances.findById(r.getEmployeeEmail()).orElseGet(()->balances.save(new LeaveBalance(r.getEmployeeEmail(),20)));
    b.setRemainingDays(Math.max(0,b.getRemainingDays()-r.getDays())); balances.save(b);
   }
  } else {
   r.setStatus(LeaveStatus.REJECTED);
   r.setStatusMessage(validationMessage != null && !"OK".equals(validationMessage) ? validationMessage :
      (comment == null || comment.isBlank() ? "Rejected by manager" : comment));
  }
  requests.save(r);
 }
 public LeaveBalance balance(String email) {
  return balances.findById(email).orElseGet(()->balances.save(new LeaveBalance(email,20)));
 }
 public void decide(Long id, DecisionRequest decision) {
  LeaveRequest r=requests.findById(id).orElseThrow();
  if(r.getStatus()!=LeaveStatus.PENDING_APPROVAL) throw new IllegalStateException("Request is not pending approval.");
  var tasks=camunda.newUserTaskSearchRequest()
    .filter(f -> f.processInstanceKey(r.getProcessInstanceKey())).send().join();
  if(tasks.getItems().isEmpty()) throw new IllegalStateException("Manager approval task is not available yet. Retry shortly.");
  var task=tasks.getItems().get(0);
  camunda.newCompleteUserTaskCommand(task.getUserTaskKey())
    .variables(Map.of("approved",decision.approved(),"managerComment",decision.comment()==null?"":decision.comment()))
    .send().join();
 }
}
