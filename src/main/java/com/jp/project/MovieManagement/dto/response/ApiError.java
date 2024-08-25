package com.jp.project.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
//        @JsonProperty("http_code") int httpCode,
        int httpCode,
        String url,
        String httpMethod,
        String message,
        String backendMessage,
        LocalDateTime timestamp,
        List<String> details
) {
}
