Inventory Management System - Java Full Stack (Basic)
----------------------------------------------------
Contents:
- Spring Boot backend (Maven)
- H2 in-memory database (for easy startup)
- REST API for CRUD (Add, Update, Delete, List)
- Simple static frontend (index.html + JS) that uses fetch() to call the API
- Audit log of changes stored in DB.

Run:
1. Make sure Java 17+ and Maven are installed.
2. From the project root run: mvn spring-boot:run
3. Open the frontend at: http://localhost:8080/index.html
4. H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:invdb, user sa, no password)

Notes:
- This is a simple, educational starter project. For production use: add authentication, validation,
  proper error handling, DTOs, pagination, and persistent DB (Postgres/MySQL), and enable CORS/security.
