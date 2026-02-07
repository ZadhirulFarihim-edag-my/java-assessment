package com.farihim.assessment.demo.user.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime lastAccessed;
}
