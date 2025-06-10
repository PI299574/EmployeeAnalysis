package com.company.model;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String salary;  // Changed from int to String
    private String managerId;
    
    public Employee(String id, String firstName, String lastName, 
                   String salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getSalary() { return salary; }
    public String getManagerId() { return managerId; }

    // Add method to get salary as integer when needed
    public int getSalaryAsInt() {
        try {
            return Integer.parseInt(salary.trim());
        } catch (NumberFormatException e) {
            return 0; // or handle error as needed
        }
    }
}