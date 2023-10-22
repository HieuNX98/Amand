package com.amand.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiResponse {

    private Integer status;

    private List<?> results;

    private Map<String, String> error;

    private String message;

    public ApiResponse() {
    }

    public ApiResponse(Integer status, Map<String, String> error) {
        this.status = status;
        this.error = error;
    }

    public ApiResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(Integer status, List<?> results) {
        this.status = status;
        this.results = results;
    }

    public ApiResponse(Integer status) {
        this.status = status;
    }
}
