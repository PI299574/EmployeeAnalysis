package com.company.service;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAnalyzerTest {
    @Test
    void testAnalyzeManagerSalaries() {
        // Setup test data
        Employee ceo = new Employee("1", "John", "CEO", "200000", null);
        Employee manager1 = new Employee("2", "Alice", "Manager", "100000", "1");
        Employee employee1 = new Employee("3", "Bob", "Dev", "80000", "2");
        Employee employee2 = new Employee("4", "Charlie", "Dev", "70000", "2");
        
        List<Employee> employees = Arrays.asList(ceo, manager1, employee1, employee2);
        
        Map<String, List<Employee>> managerSubordinateMap = new HashMap<>();
        managerSubordinateMap.put("1", Arrays.asList(manager1));
        managerSubordinateMap.put("2", Arrays.asList(employee1, employee2));
        
        // Execute
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        AnalysisResult result = analyzer.analyzeEmployees(employees, managerSubordinateMap);
        
        // Verify
     
       
        assertEquals(1, result.getOverpaidManagers().size());
        assertEquals(0, result.getLongReportingLineEmployees().size());
    }

    @Test
    void testAnalyzeReportingLines() {
        // Setup test data with a long reporting line
        Employee ceo = new Employee("1", "John", "CEO", "200000", null);
        Employee m1 = new Employee("2", "M1", "Manager", "150000", "1");
        Employee m2 = new Employee("3", "M2", "Manager", "120000", "2");
        Employee m3 = new Employee("4", "M3", "Manager", "100000", "3");
        Employee m4 = new Employee("5", "M4", "Manager", "90000", "4");
        Employee e1 = new Employee("6", "E1", "Employee", "80000", "5");
        
        List<Employee> employees = Arrays.asList(ceo, m1, m2, m3, m4, e1);
        
        Map<String, List<Employee>> managerSubordinateMap = new HashMap<>();
        managerSubordinateMap.put("1", Arrays.asList(m1));
        managerSubordinateMap.put("2", Arrays.asList(m2));
        managerSubordinateMap.put("3", Arrays.asList(m3));
        managerSubordinateMap.put("4", Arrays.asList(m4));
        managerSubordinateMap.put("5", Arrays.asList(e1));
        
        // Execute
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        AnalysisResult result = analyzer.analyzeEmployees(employees, managerSubordinateMap);
        
        // Verify
        assertEquals(1, result.getLongReportingLineEmployees().size());
       
    }
}