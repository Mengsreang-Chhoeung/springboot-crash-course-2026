package com.example.taskapi.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
    @Schema(example = "Learn Spring Boot") @NotBlank @Size(max = 120) String title,
    @Schema(example = "Build my first API") String description,
    @Schema(defaultValue = "false") Boolean done) {
}
