# BPMN-camunda

BPMN Process Modeling Assignment

Overview : This repository contains the BPMN diagrams developed for the assignment using basic BPMN building blocks.
The assignment demonstrates the modeling of three real-world business processes:
1. Employee Leave Approval
2. Online Purchase Order Processing
3. IT Service Request

The models use standard BPMN elements such as - 
Start Events, Tasks, Exclusive Gateways,multiple paths and End Events to represent the flow of each process

Scenario 1: Employee Leave Approval
Description- The Employee Leave Approval process models how an employee submits a leave request through the company's HR system.
The system first checks the employee's available leave balance. If sufficient leave is available, the request is forwarded to the manager for approval. The process then follows either an approval or rejection path.
If the manager approves the request, the system updates the employee's leave balance and sends an approval notification. If the manager rejects the request, a rejection notification is sent.
If the employee does not have sufficient leave balance, the system sends an insufficient-balance notification.

Process Flow
Employee submits leave request
↓
Check leave balance
↓
Exclusive Gateway: Is sufficient leave balance available?
Yes → Send request to manager
No → Send insufficient-balance notification → End
If sufficient balance is available:
Manager approval decision
Approved → Update leave balance → Send approval notification → End
Rejected → Send rejection notification → End

Scenario 2: Online Purchase Order Processing
Description-The Online Purchase Order Processing process represents the workflow followed when a customer places an order online.After the customer places an order, the system checks whether the requested product is available.
If the product is unavailable, the customer receives an out-of-stock notification and the process ends.
If the product is available, the system processes the payment. A successful payment allows the order to continue through confirmation, product preparation, and shipping.
If the payment fails, the customer receives a payment-failure notification and the process ends.
After the product is shipped, the customer receives a shipping confirmation.

Process Flow
Customer places order
↓
Check product availability
↓
Exclusive Gateway: Is product available?
No → Notify customer that product is out of stock → End
Yes → Process payment
↓
Exclusive Gateway: Was payment successful?
No → Notify customer about payment failure → End
Yes → Confirm order
↓
Prepare product for shipment
↓
Ship order
↓
Send shipping confirmation
↓
End

Scenario 3: IT Service Request
Description-The IT Service Request process models how an organization handles an employee's IT support request.
The process begins when an employee submits an IT support request. The IT help desk registers the request and checks the severity of the reported problem.An Exclusive Gateway determines whether the problem is low or high severity.
Low-severity problems are assigned to a support technician.
High-severity problems are assigned to a senior technician.
The assigned technician investigates the problem. Another Exclusive Gateway determines whether the problem can be resolved internally.
If the problem can be resolved, the technician fixes it.
If it cannot be resolved internally, the problem is escalated to an external service provider.
After the problem is resolved, the help desk updates the request status and sends a resolution notification to the employee.

Process Flow
Employee submits IT support request
↓
Register request
↓
Check problem severity
↓
Exclusive Gateway: What is the severity?
Low severity → Assign to support technician
High severity → Assign to senior technician
Both paths continue to:
Investigate problem
↓
Exclusive Gateway: Can the problem be resolved internally?
Yes → Fix problem
No → Escalate to external service provider
↓
Update request status
↓
Send resolution notification
↓
End

 
