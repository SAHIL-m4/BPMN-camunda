package edu.srmist.leave.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class LeaveWorkers {
    private static final Logger log = LoggerFactory.getLogger(LeaveWorkers.class);

    @JobWorker(type = "validate-leave")
    public Map<String, Object> validateLeave(
            @Variable String fromDate,
            @Variable String toDate) {

        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);
        long days = ChronoUnit.DAYS.between(from, to) + 1;

        boolean valid = days > 0 && !from.isBefore(LocalDate.now());
        boolean autoApproved = valid && days <= 2;

        Map<String, Object> variables = new HashMap<>();
        variables.put("leaveDays", days);
        variables.put("valid", valid);
        variables.put("approved", autoApproved);
        return variables;
    }

    @JobWorker(type = "notify-student")
    public Map<String, Object> notifyStudent(
            @Variable String studentName,
            @Variable String regNo,
            @Variable Boolean approved,
            @Variable String remarks) {

        String status = Boolean.TRUE.equals(approved) ? "APPROVED" : "REJECTED";
        log.info("Notification to {} ({}): leave is {}. Remarks: {}",
                studentName, regNo, status, remarks == null ? "" : remarks);

        return Map.of("leaveStatus", status);
    }
}
