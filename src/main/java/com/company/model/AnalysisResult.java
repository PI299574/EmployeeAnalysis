package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
    private List<String> underpaidManagers = new ArrayList<>();
    private List<String> overpaidManagers = new ArrayList<>();
    private List<String> longReportingLineEmployees = new ArrayList<>();

    // Getters and setters
    public void addUnderpaidManager(String message) {
        underpaidManagers.add(message);
    }

    public void addOverpaidManager(String message) {
        overpaidManagers.add(message);
    }

    public void addLongReportingLineEmployee(String message) {
        longReportingLineEmployees.add(message);
    }

    public List<String> getUnderpaidManagers() {
        return underpaidManagers;
    }

    public List<String> getOverpaidManagers() {
        return overpaidManagers;
    }

    public List<String> getLongReportingLineEmployees() {
        return longReportingLineEmployees;
    }
}