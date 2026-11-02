package com.example.leave.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Entity @Getter @Setter @NoArgsConstructor
public class LeaveBalance {
 @Id private String employeeEmail;
 private int remainingDays;
 public LeaveBalance(String email, int days) { this.employeeEmail=email; this.remainingDays=days; }
}
