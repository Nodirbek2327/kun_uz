package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO {
    private boolean status;
    private String message;
    private Object data;

    public ApiResponseDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponseDTO(boolean status, Object data) {
        this.status = status;
        this.data = data;
    }

}
