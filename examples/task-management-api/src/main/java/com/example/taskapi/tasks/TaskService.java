package com.example.taskapi.tasks;

import com.example.taskapi.tasks.dto.CreateTaskRequest;
import com.example.taskapi.tasks.dto.TaskResponse;
import com.example.taskapi.tasks.dto.UpdateTaskRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream().map(TaskResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse findOne(Long id) {
        return TaskResponse.from(getTask(id));
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        ensureTitleIsFree(request.title());
        Task task = new Task(request.title(), request.description(), Boolean.TRUE.equals(request.done()));
        return TaskResponse.from(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse update(Long id, UpdateTaskRequest request) {
        Task task = getTask(id);
        if (request.title() != null && !request.title().equals(task.getTitle())) {
            ensureTitleIsFree(request.title());
            task.setTitle(request.title());
        }
        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.done() != null) {
            task.setDone(request.done());
        }
        return TaskResponse.from(task); // dirty checking saves the changes at commit
    }

    @Transactional
    public void remove(Long id) {
        taskRepository.delete(getTask(id));
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task " + id + " not found"));
    }

    private void ensureTitleIsFree(String title) {
        if (taskRepository.existsByTitle(title)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A task titled '" + title + "' already exists");
        }
    }
}
