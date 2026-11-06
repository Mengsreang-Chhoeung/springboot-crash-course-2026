package com.example.task_management_api.tasks.dto;

import java.time.Instant;

import com.example.task_management_api.tasks.Task;

public record TaskResponse(Long id, String title, String description, boolean done, Instant createdAt) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.isDone(), task.getCreatedAt());
    }
}
