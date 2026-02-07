package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.domain.User;
import com.farihim.assessment.demo.dto.request.UserRequest;
import com.farihim.assessment.demo.dto.response.UserResponse;
import com.farihim.assessment.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUserAndLogCreation() {
        UserRequest request = new UserRequest();
        request.setName("Jack Sparrow");
        request.setEmail("jack.sparrow@example.com");

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertEquals(savedUser.getId(), response.getId());
        assertEquals(savedUser.getName(), response.getName());
        assertEquals(savedUser.getEmail(), response.getEmail());

        verify(userRepository).save(any(User.class));
        verify(auditLogService).logUserCreation(savedUser.getId(), savedUser.getEmail());
    }

    @Test
    void getAllUsers_shouldReturnMappedUserResponses() {
        User user1 = User.builder().id(UUID.randomUUID()).name("Emma Maembong").email("emma.m@test.com").build();
        User user2 = User.builder().id(UUID.randomUUID()).name("Emma Watson").email("emma.w@test.com").build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(u -> u.getName().equals("Emma Maembong")));
        assertTrue(responses.stream().anyMatch(u -> u.getName().equals("Emma Watson")));
    }

    @Test
    void getAllUsersPaged_shouldReturnPagedUsers() {
        User user = User.builder().id(UUID.randomUUID()).name("Emma Stone").email("emma.s@test.com").build();
        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<UserResponse> responsePage = userService.getAllUsersPaged(0, 10, "name", "ASC");

        assertEquals(1, responsePage.getContent().size());
        assertEquals("Emma Stone", responsePage.getContent().get(0).getName());
    }

    @Test
    void getAllUsersPaged_shouldThrowForInvalidSortField() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.getAllUsersPaged(0, 10, "invalidField", "ASC")
        );
    }

    @Test
    void getUserById_shouldReturnUserAndUpdateLastAccessed() {
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).name("Emma Fatima").email("emma.f@test.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserResponse response = userService.getUserById(userId);

        assertEquals(userId, response.getId());
        assertNotNull(user.getLastAccessed());

        verify(userRepository).save(user);
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(userId));
    }

    @Test
    void updateUser_shouldUpdateAndLogChanges() {
        UUID userId = UUID.randomUUID();
        User existingUser = User.builder().id(userId).name("OldName").email("old@test.com").build();
        UserRequest request = new UserRequest();
        request.setName("NewName");
        request.setEmail("new@test.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserResponse response = userService.updateUser(userId, request);

        assertEquals("NewName", response.getName());
        assertEquals("new@test.com", response.getEmail());

        verify(auditLogService).logUserUpdate(userId, "OldName", "old@test.com", "NewName", "new@test.com");
    }

    @Test
    void deleteUser_shouldDeleteAndLog() {
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).email("delete@test.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(auditLogService).logUserDeletion(userId, "delete@test.com");
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_shouldThrowIfNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(userId));
    }
}
