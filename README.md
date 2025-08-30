 Overview
A Java EE 3-tier web application for billing and customer management at Pahana Edu.  
Implements login, customer management, product management, bill generation, and reporting.

 Technologies Used
- Java EE (Servlets, JSP)
- JDBC + MySQL
- Apache Tomcat
- JUnit (Testing)
- Git & GitHub for version control
- Maven for build management

 Architecture
- Presentation Layer: JSP pages (login, customers, products, cart, bill, dashboard, help).
- Business Layer: Servlets and service classes (LoginServlet, CheckoutServlet, etc.).
- Data Layer:DAO classes with Singleton DB connection.

Features
- Secure login with roles (Admin, Cashier)
- Customer registration & management
- Product stock management
- Bill generation & cart system
- Reports: Customer billing history & monthly revenue
- Input validation (email, account numbers, etc.)

Testing
Implemented using JUnit & TDD.  
Examples: login validation, bill calculation, stock updates.  

GitHub & Version Control
- Branching strategy: feature branches (dao, servlet, model, view).
- Over 28 commits with meaningful messages.
- GitHub Actions for CI/CD.

 Deployment
- Runs on Apache Tomcat
- Database: MySQL
- Import project via Maven
