package com.example.leave.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor
public class LeaveRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String employeeName;
 private String employeeEmail;
 private String leaveType;
 private LocalDate startDate;
 private LocalDate endDate;
 private int days;
 @Column(length=1000) private String reason;
 @Enumerated(EnumType.STRING) private LeaveStatus status = LeaveStatus.SUBMITTED;
 @Column(length=1000) private String statusMessage;
 private Long processInstanceKey;
 private Instant createdAt = Instant.now();
}
