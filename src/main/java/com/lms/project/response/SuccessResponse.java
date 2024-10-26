package com.lms.project.response;
import lombok.Data;
import java.io.Serializable;
@Data
public class SuccessResponse<T>implements Serializable {
    private int statusCode = 200;
    private String statusMessage = "Success";
    private transient T data;
}
