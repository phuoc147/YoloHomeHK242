package iot.controllers;

import lombok.Builder;

@Builder
public class ApiResponse<T> {
    private String message;
    private T data;
    private String error;
}