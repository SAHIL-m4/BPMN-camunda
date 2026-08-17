# BPMN-camunda

BPMN Process Modeling Assignment

Overview : This repository contains the BPMN diagrams developed for the assignment using basic BPMN building blocks.

The models use standard BPMN elements such as - 
Start Events, Tasks, Exclusive Gateways,multiple paths and End Events to represent the flow of each process

📙Experiement week number : 1

📄Scenario 1: Employee Leave Approval

Description- The Employee Leave Approval process models how an employee submits a leave request through the company's HR system.
The system first checks the employee's available leave balance. If sufficient leave is available, the request is forwarded to the manager for approval. The process then follows either an approval or rejection path.
If the manager approves the request, the system updates the employee's leave balance and sends an approval notification. If the manager rejects the request, a rejection notification is sent.
If the employee does not have sufficient leave balance, the system sends an insufficient-balance notification.


📄Scenario 2: Online Purchase Order Processing

Description-The Online Purchase Order Processing process represents the workflow followed when a customer places an order online.After the customer places an order, the system checks whether the requested product is available.
If the product is unavailable, the customer receives an out-of-stock notification and the process ends.
If the product is available, the system processes the payment. A successful payment allows the order to continue through confirmation, product preparation, and shipping.
If the payment fails, the customer receives a payment-failure notification and the process ends.
After the product is shipped, the customer receives a shipping confirmation.


📄Scenario 3: IT Service Request

Description-The IT Service Request process models how an organization handles an employee's IT support request.
The process begins when an employee submits an IT support request. The IT help desk registers the request and checks the severity of the reported problem.An Exclusive Gateway determines whether the problem is low or high severity.
Low-severity problems are assigned to a support technician.
High-severity problems are assigned to a senior technician.
The assigned technician investigates the problem. Another Exclusive Gateway determines whether the problem can be resolved internally.
If the problem can be resolved, the technician fixes it.
If it cannot be resolved internally, the problem is escalated to an external service provider.
After the problem is resolved, the help desk updates the request status and sends a resolution notification to the employee.

📙 Experiement week number : 2

📄 Scenario 1: Hotel Room Reservation 
This BPMN model represents the process of booking a hotel room through an online reservation system.
The process starts when a guest submits a room booking request. The system checks room availability. If no room is available, the guest is notified and the process ends. If a room is available, the system requests an advance payment. If the payment is successful, the booking is confirmed, a booking reference number is generated, and a confirmation email is sent to the guest. If payment fails, the guest is notified and the process ends.

📄 Scenario 2: Loan Application Processing
This BPMN model represents the process of handling a customer's personal loan application at a bank.
The process begins when the customer submits a loan application. The bank verifies the applicant's documents and credit score. If the documents are invalid or incomplete, the application is rejected and the customer is notified. If the documents are valid, the applicant's eligibility is checked based on credit score and income. Eligible applications are forwarded to a loan officer for final approval. If approved, the loan amount is disbursed and an approval notification is sent. If rejected, the customer receives a rejection notification.

📄Scenario 3: Job Applicant Recruitment Process 
This BPMN model represents the recruitment process for a job application received by a company's HR department.
The process starts when a candidate submits an application online. The HR system screens the application against the minimum eligibility criteria. Candidates who do not meet the criteria receive a rejection notification. Eligible candidates proceed to a technical interview. Candidates who pass the technical interview proceed to the HR/managerial round. If the candidate is selected, an offer letter is generated and sent to the candidate. Candidates who are rejected at any stage receive a rejection notification.
