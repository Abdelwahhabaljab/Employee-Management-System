package com.example.employee_management_system.service;


public interface AuditLogService {
    void logChange(Long employeeId, String action, String details, String performedBy);
}
