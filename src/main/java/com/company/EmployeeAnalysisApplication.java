package com.company;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import com.company.service.EmployeeAnalyzer;
import com.company.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class EmployeeAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeAnalysisApplication.class, args);
    }

    @Bean
    CommandLineRunner run(EmployeeService employeeService, EmployeeAnalyzer analyzer) {
        return args -> {
            try {
                // Read and parse the file
                List<Employee> employees = employeeService.readEmployeesFromFile();

                // Build manager-subordinate relationships
                Map<String, List<Employee>> managerSubordinateMap =
                        employeeService.buildManagerSubordinateMap(employees);

                // Analyze the data
                AnalysisResult result = analyzer.analyzeEmployees(employees, managerSubordinateMap);

                // Print results
                printResults(result);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    private void printResults(AnalysisResult result) {
        System.out.println("=== Analysis Results ===");

        System.out.println("\nUnderpaid Managers:");
        if (result.getUnderpaidManagers().isEmpty()) {
            System.out.println("No underpaid managers found");
        } else {
            result.getUnderpaidManagers().forEach(System.out::println);
        }

        System.out.println("\nOverpaid Managers:");
        if (result.getOverpaidManagers().isEmpty()) {
            System.out.println("No overpaid managers found");
        } else {
            result.getOverpaidManagers().forEach(System.out::println);
        }

        System.out.println("\nEmployees with Long Reporting Lines:");
        if (result.getLongReportingLineEmployees().isEmpty()) {
            System.out.println("No employees with long reporting lines found");
        } else {
            result.getLongReportingLineEmployees().forEach(System.out::println);
        }
    }
}