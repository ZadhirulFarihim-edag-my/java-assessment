package com.farihim.assessment.demo.controller;

import com.farihim.assessment.demo.dto.request.UserRequest;
import com.farihim.assessment.demo.dto.response.UserResponse;
import com.farihim.assessment.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_shouldReturn201() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Farihim");
        request.setEmail("farihim@example.com");

        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("Farihim")
                .email("farihim@example.com")
                .build();

        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.name").value(response.getName()));
    }

    @Test
    void getUserById_shouldUpdateLastAccessed() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("Farihim")
                .email("farihim@example.com")
                .lastAccessed(LocalDateTime.now())
                .build();

        when(userService.getUserById(response.getId())).thenReturn(response);

        mockMvc.perform(get("/api/users/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastAccessed").exists());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Labu");
        request.setEmail("john.labu@example.com");

        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("John Labu")
                .email("john.labu@example.com")
                .build();

        when(userService.updateUser(eq(response.getId()), any())).thenReturn(response);

        mockMvc.perform(put("/api/users/" + response.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Labu"));
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        var userId = UUID.randomUUID();
        var userResponse = UserResponse.builder()
                .id(userId)
                .name("Thanos")
                .build();

        when(userService.getAllUsers()).thenReturn(List.of(userResponse));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userId.toString()));
    }

    @Test
    void deleteUser_existingUser_shouldReturn204() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@example.com")
                .build();

        mockMvc.perform(delete("/api/users/" + response.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_nonExistentUser_shouldReturn404() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        doThrow(new NoSuchElementException("User not found"))
                .when(userService).deleteUser(nonExistentId);

        mockMvc.perform(delete("/api/users/" + nonExistentId))
                .andExpect(status().isNotFound());
    }
}
