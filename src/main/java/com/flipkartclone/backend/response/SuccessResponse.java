package com.flipkartclone.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SuccessResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private String path;
}