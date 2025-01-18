package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.AuditLog;
import com.example.employee_management_system.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logChange(Long employeeId, String action, String details, String performedBy) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEmployeeId(employeeId);
        auditLog.setAction(action);
        auditLog.setDetails(details);
        auditLog.setPerformedBy(performedBy);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }
}
