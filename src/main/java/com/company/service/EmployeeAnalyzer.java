package com.company.service;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class EmployeeAnalyzer {
    public AnalysisResult analyzeEmployees(List<Employee> employees, 
                                         Map<String, List<Employee>> managerSubordinateMap) {
        AnalysisResult result = new AnalysisResult();
        
        // Analyze manager salaries
        analyzeManagerSalaries(employees, managerSubordinateMap, result);
        
        // Analyze reporting line depth
        analyzeReportingLines(employees, managerSubordinateMap, result);
        
        return result;
    }

    private void analyzeManagerSalaries(List<Employee> employees, 
            Map<String, List<Employee>> managerSubordinateMap,
            AnalysisResult result) {
for (Employee manager : employees) {
List<Employee> subordinates = managerSubordinateMap.get(manager.getId());

if (subordinates != null && !subordinates.isEmpty()) {
double avgSubordinateSalary = calculateAverageSalary(subordinates);
double minExpectedSalary = avgSubordinateSalary * 1.2;
double maxExpectedSalary = avgSubordinateSalary * 1.5;

double managerSalary = manager.getSalaryAsInt();

if (managerSalary < minExpectedSalary) {
double difference = minExpectedSalary - managerSalary;
result.addUnderpaidManager(String.format(
"%s %s (%s) earns %.2f less than required minimum (%.2f vs %.2f)",
manager.getFirstName(), manager.getLastName(), manager.getId(),
difference, managerSalary, minExpectedSalary));
} else if (managerSalary > maxExpectedSalary) {
double difference = managerSalary - maxExpectedSalary;
result.addOverpaidManager(String.format(
"%s %s (%s) earns %.2f more than allowed maximum (%.2f vs %.2f)",
manager.getFirstName(), manager.getLastName(), manager.getId(),
difference, managerSalary, maxExpectedSalary));
}
}
}
}

private double calculateAverageSalary(List<Employee> employees) {
return employees.stream()
.mapToInt(Employee::getSalaryAsInt)
.average()
.orElse(0);
}
    private void analyzeReportingLines(List<Employee> employees,
                                     Map<String, List<Employee>> managerSubordinateMap,
                                     AnalysisResult result) {
        Employee ceo = employees.stream()
            .filter(e -> e.getManagerId() == null)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("CEO not found"));
        
        Map<String, Integer> depthMap = new HashMap<>();
        depthMap.put(ceo.getId(), 0);
        
        for (Employee employee : employees) {
            if (!employee.getId().equals(ceo.getId())) {
                int depth = calculateReportingDepth(employee, employees, depthMap);
                if (depth > 4) {
                    result.addLongReportingLineEmployee(String.format(
                        "%s has a reporting line %d levels deep (more than 4 allowed)",
                        employee, depth));
                }
            }
        }
    }

    private int calculateReportingDepth(Employee employee, 
                                      List<Employee> employees,
                                      Map<String, Integer> depthMap) {
        if (depthMap.containsKey(employee.getId())) {
            return depthMap.get(employee.getId());
        }
        
        if (employee.getManagerId() == null) {
            depthMap.put(employee.getId(), 0);
            return 0;
        }
        
        Employee manager = employees.stream()
            .filter(e -> e.getId().equals(employee.getManagerId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Manager not found for employee: " + employee.getId()));
        
        int managerDepth = calculateReportingDepth(manager, employees, depthMap);
        int employeeDepth = managerDepth + 1;
        depthMap.put(employee.getId(), employeeDepth);
        
        return employeeDepth;
    }
}