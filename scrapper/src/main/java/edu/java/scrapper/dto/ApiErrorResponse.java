package edu.java.scrapper.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ApiErrorResponse {
    @NonNull
    private final String description;
    @NonNull
    private final String code;
    @NonNull
    private final String exceptionName;
    @NonNull
    private final String exceptionMessage;
    @NonNull
    private final StackTraceElement[] stackTrace;
}
