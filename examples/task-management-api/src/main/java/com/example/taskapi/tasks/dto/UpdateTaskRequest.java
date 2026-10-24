package com.example.taskapi.tasks.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Every field is optional: a field left out of the JSON stays as it is. */
public record UpdateTaskRequest(
    @Size(max = 120) @Pattern(regexp = ".*\\S.*", message = "must not be blank") String title,
    String description,
    Boolean done) {
}
