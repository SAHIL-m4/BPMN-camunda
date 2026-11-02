package com.example.leave.api;
import com.example.leave.domain.*;
import com.example.leave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/leaves")
@CrossOrigin(origins="http://localhost:5173")
public class LeaveController {
 private final LeaveService service; private final LeaveRequestRepository requests;
 public LeaveController(LeaveService service, LeaveRequestRepository requests) { this.service=service; this.requests=requests; }
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public LeaveRequest submit(@Valid @RequestBody SubmitLeaveRequest body) { return service.submit(body); }
 @GetMapping public List<LeaveRequest> mine(@RequestParam String employeeEmail) {
  return requests.findByEmployeeEmailOrderByCreatedAtDesc(employeeEmail);
 }
 @GetMapping("/pending") public List<LeaveRequest> pending() {
  return requests.findByStatusOrderByCreatedAtAsc(LeaveStatus.PENDING_APPROVAL);
 }
 @PostMapping("/{id}/decision") @ResponseStatus(HttpStatus.NO_CONTENT)
 public void decide(@PathVariable Long id,@RequestBody DecisionRequest body) { service.decide(id,body); }
 @GetMapping("/balance") public Map<String,Integer> balance(@RequestParam String employeeEmail) {
  return Map.of("remainingDays",service.balance(employeeEmail).getRemainingDays());
 }
}
