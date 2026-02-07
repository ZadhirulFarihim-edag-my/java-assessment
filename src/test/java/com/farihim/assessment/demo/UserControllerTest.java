package com.farihim.assessment.demo;

import com.farihim.assessment.demo.user.dto.UserRequest;
import com.farihim.assessment.demo.user.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrudOperations() throws Exception {
        // 1. Create User
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        assertThat(response.getName()).isEqualTo("John Doe");
        Long userId = response.getId();

        // 2. Get User (and verify @Transactional update of lastAccessed)
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(r -> {
                    UserResponse resp = objectMapper.readValue(r.getResponse().getContentAsString(), UserResponse.class);
                    assertThat(resp.getLastAccessed()).isNotNull();
                });

        // 3. Update User
        request.setName("Jane Doe");
        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(r -> {
                    UserResponse resp = objectMapper.readValue(r.getResponse().getContentAsString(), UserResponse.class);
                    assertThat(resp.getName()).isEqualTo("Jane Doe");
                });

        // 4. Pagination
        mockMvc.perform(get("/api/users/paged?page=0&size=10"))
                .andExpect(status().isOk());

        // 5. External API
        mockMvc.perform(get("/api/external/posts/1"))
                .andExpect(status().isOk());

        // 6. Delete User
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());
    }
}
