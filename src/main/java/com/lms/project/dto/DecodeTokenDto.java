package com.lms.project.dto;
import lombok.Data;
@Data
public class DecodeTokenDto
{
    private String sub;
    private int iat;
    private int exp;
    private String id;
    private String email;
    private String mobile;
}
