package com.company.service;

import com.company.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Value("${employee.file.path}")
    private Resource employeeFileResource;


    public List<Employee> readEmployeesFromFile() throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (InputStream is = employeeFileResource.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                String[] values = line.split(",");
                String managerId = values.length > 4 ? values[4].trim() : null;

                employees.add(new Employee(
                        values[0].trim(),
                        values[1].trim(),
                        values[2].trim(),
                        values[3].trim(),  // Salary as string
                        managerId
                ));
            }
        }

        return employees;
    }

    public Map<String, List<Employee>> buildManagerSubordinateMap(List<Employee> employees) {
        Map<String, List<Employee>> managerSubordinateMap = new HashMap<>();

        for (Employee employee : employees) {
            if (employee.getManagerId() != null && !employee.getManagerId().isEmpty()) {
                managerSubordinateMap
                        .computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>())
                        .add(employee);
            }
        }

        return managerSubordinateMap;
    }

    public Employee findCeo(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getManagerId() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CEO not found"));
    }
}