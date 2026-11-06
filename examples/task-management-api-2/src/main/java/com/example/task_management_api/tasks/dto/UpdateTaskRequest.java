package com.example.task_management_api.tasks.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @Size(max = 120) @Pattern(regexp = ".*\\S.*", message = "must not be blank") String title,
        String description,
        Boolean done) {

}
