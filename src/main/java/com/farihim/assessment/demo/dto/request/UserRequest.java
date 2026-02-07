package com.farihim.assessment.demo.dto.request;

import com.farihim.assessment.demo.validator.ValidEmail;
import com.farihim.assessment.demo.validator.ValidString;
import lombok.Data;

@Data
public class UserRequest {
    @ValidString
    private String name;
    @ValidEmail
    private String email;
}
