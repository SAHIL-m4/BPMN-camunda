# Integration test cases

| ID | Test | Input/action | Expected result |
|---|---|---|---|
| T01 | Submit valid annual leave | Weekday range within balance | Request saved and process started |
| T02 | Approve request | Manager approves with comment | APPROVED; annual balance reduced once |
| T03 | Reject request | Manager rejects with comment | REJECTED; balance unchanged |
| T04 | Insufficient balance | Request more annual days than available | Validation rejects before manager approval |
| T05 | Weekend-only range | Saturday to Sunday | Rejected because there are zero working days |
| T06 | Invalid date order | End date before start date | API returns HTTP 400 |
| T07 | Task not indexed yet | Decide immediately after submit | HTTP 409; retry after task becomes available |
| T08 | Inspect workflow | Open process instance in Operate | Variables and executed path are visible |

## Evidence/screenshots to capture yourself
1. Camunda Modeler showing the executable `leave-request` BPMN.
2. React Apply page with a sample request.
3. My Requests showing the submitted request.
4. Manager Inbox with a pending request.
5. Approved request and updated leave balance.
6. Rejected/invalid request.
7. Camunda Operate process instance and variables.
8. H2 console showing request and balance records.
9. `docker compose ps` showing application containers.
10. Browser showing the containerised frontend at `http://localhost:3000`.

Do not mark a test as passed until you have actually run it and captured the result.
