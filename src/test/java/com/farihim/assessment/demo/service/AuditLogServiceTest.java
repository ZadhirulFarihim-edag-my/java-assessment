package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.domain.AuditLog;
import com.farihim.assessment.demo.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    @Test
    void logUserCreation_shouldSaveAuditLog() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";

        auditLogService.logUserCreation(userId, email);

        ArgumentCaptor<AuditLog> captor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository).save(captor.capture());

        AuditLog savedLog = captor.getValue();
        assertEquals("USER_CREATED", savedLog.getAction());
        assertEquals(userId, savedLog.getUserId());
        assertTrue(savedLog.getDetails().contains(email));
        assertNotNull(savedLog.getTimestamp());
    }

    @Test
    void logUserUpdate_shouldSaveAuditLogWithOldAndNewValues() {
        UUID userId = UUID.randomUUID();

        auditLogService.logUserUpdate(userId, "OldName", "old@example.com", "NewName", "new@example.com");

        ArgumentCaptor<AuditLog> captor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository).save(captor.capture());

        AuditLog savedLog = captor.getValue();
        assertEquals("USER_UPDATED", savedLog.getAction());
        assertEquals(userId, savedLog.getUserId());
        assertTrue(savedLog.getDetails().contains("Old: [name=OldName, email=old@example.com]"));
        assertTrue(savedLog.getDetails().contains("New: [name=NewName, email=new@example.com]"));
        assertNotNull(savedLog.getTimestamp());
    }

    @Test
    void logUserDeletion_shouldSaveAuditLog() {
        UUID userId = UUID.randomUUID();
        String email = "delete@example.com";

        auditLogService.logUserDeletion(userId, email);

        ArgumentCaptor<AuditLog> captor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository).save(captor.capture());

        AuditLog savedLog = captor.getValue();
        assertEquals("USER_DELETED", savedLog.getAction());
        assertEquals(userId, savedLog.getUserId());
        assertTrue(savedLog.getDetails().contains(email));
        assertNotNull(savedLog.getTimestamp());
    }

    @Test
    void logUserCreation_shouldNotThrowIfRepositoryFails() {
        UUID userId = UUID.randomUUID();
        String email = "fail@example.com";

        doThrow(new RuntimeException("DB error")).when(auditLogRepository).save(any());

        assertDoesNotThrow(() -> auditLogService.logUserCreation(userId, email));
        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void logUserUpdate_shouldNotThrowIfRepositoryFails() {
        UUID userId = UUID.randomUUID();

        doThrow(new RuntimeException("DB error")).when(auditLogRepository).save(any());

        assertDoesNotThrow(() -> auditLogService.logUserUpdate(
                userId, "Old", "old@example.com", "New", "new@example.com"
        ));
        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void logUserDeletion_shouldNotThrowIfRepositoryFails() {
        UUID userId = UUID.randomUUID();
        String email = "faildelete@example.com";

        doThrow(new RuntimeException("DB error")).when(auditLogRepository).save(any());

        assertDoesNotThrow(() -> auditLogService.logUserDeletion(userId, email));
        verify(auditLogRepository).save(any(AuditLog.class));
    }
}
