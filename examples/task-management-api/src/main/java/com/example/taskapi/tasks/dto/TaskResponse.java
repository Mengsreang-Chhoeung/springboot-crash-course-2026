package com.example.taskapi.tasks.dto;

import com.example.taskapi.tasks.Task;
import java.time.Instant;

public record TaskResponse(Long id, String title, String description, boolean done, Instant createdAt) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
            task.isDone(), task.getCreatedAt());
    }
}
