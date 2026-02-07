package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.domain.AuditLog;
import com.farihim.assessment.demo.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserCreation(UUID userId, String email) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .id(UUID.randomUUID())
                    .action("USER_CREATED")
                    .userId(userId)
                    .details("User created with email: " + email)
                    .timestamp(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
            log.info("Audit log created for user creation: {}", userId);
        } catch (Exception e) {
            log.error("Failed to create audit log for user creation: {}", userId, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserUpdate(UUID userId, String oldName, String oldEmail,
                              String newName, String newEmail) {
        try {
            String details = String.format(
                    "User updated - Old: [name=%s, email=%s], New: [name=%s, email=%s]",
                    oldName, oldEmail, newName, newEmail
            );

            AuditLog auditLog = AuditLog.builder()
                    .id(UUID.randomUUID())
                    .action("USER_UPDATED")
                    .userId(userId)
                    .details(details)
                    .timestamp(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
            log.info("Audit log created for user update: {}", userId);
        } catch (Exception e) {
            log.error("Failed to create audit log for user update: {}", userId, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserDeletion(UUID userId, String email) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .id(UUID.randomUUID())
                    .action("USER_DELETED")
                    .userId(userId)
                    .details("User deleted with email: " + email)
                    .timestamp(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
            log.info("Audit log created for user deletion: {}", userId);
        } catch (Exception e) {
            log.error("Failed to create audit log for user deletion: {}", userId, e);
        }
    }
}