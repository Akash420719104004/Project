package com.lms.project.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {
    private String refreshToken;
}