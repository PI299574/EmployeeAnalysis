# Employee Structure Analysis Application

## Overview
This Spring Boot application analyzes company organizational structure by:
- Identifying underpaid/overpaid managers (earning <20% or >50% of their team's average)
- Detecting employees with reporting lines >4 levels deep
- Processing employee data from a CSV file

## Prerequisites
- Java 17+
- Maven 3.8+
- Spring Boot 3.2+

## Quick Start

1. **Add your CSV file**  
   Place `employees.csv` in: src/main/resources/employees.csv
2. **Run the application**:
bash
mvn spring-boot:run
CSV File Format

Required header row with exact column names:

csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123

Running Options
1. Default (using resources file)
bash
mvn spring-boot:run
2. Custom CSV location
bash
mvn spring-boot:run -Dspring-boot.run.arguments="--employee.file.path=file:/absolute/path/to/your.csv"
3. As executable JAR
bash
mvn clean package
java -jar target/employee-analysis-1.0-SNAPSHOT.jar --employee.file.path=classpath:employees.csv
Expected Output

=== Analysis Results ===

Underpaid Managers:
Joe Doe (123) earns 4000.00 less than required minimum (60000.00 vs 64000.00)

Overpaid Managers:
Alice Hasacat (300) earns 5000.00 more than allowed maximum (50000.00 vs 45000.00)

Employees with Long Reporting Lines:
Brett Hardleaf (305) has a reporting line 4 levels deep (more than 4 allowed)
Configuration Options
application.properties:

properties
# Default (classpath)
employee.file.path=classpath:employees.csv
# Filesystem path
#employee.file.path=file:/path/to/employees.csv
Command line override:

bash
--employee.file.path=file:/custom/path.csv
Build & Test
bash
# Build
mvn clean package

# Run tests
mvn test

Project Structure
text
src/
├── main/
│   ├── java/com/company/
│   │   ├── model/       # Employee, AnalysisResult
│   │   ├── service/     # Business logic
│   │   └── EmployeeAnalysisApplication.java # Main class
│   └── resources/
│       ├── application.properties
│       └── employees.csv
└── test/              # JUnit tests

Troubleshooting
Issue	Solution
File not found	Verify file exists in src/main/resources
Number format error	Check CSV for non-numeric salaries
Version conflicts	Run mvn clean install -U


   
