package com.artur.todo_app.service;

import com.artur.todo_app.dto.TaskRequestDto;
import com.artur.todo_app.exception.TaskNotFoundException;
import com.artur.todo_app.model.Task;
import com.artur.todo_app.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task saveTask(TaskRequestDto taskDto) {
        if (taskDto.getTitle() == null || taskDto.getTitle().isBlank()) {
            log.warn("Attempt to save task with empty title");
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setCompleted(taskDto.getCompleted() != null ? taskDto.getCompleted() : false);

        Task savedTask = taskRepository.save(task);
        log.info("Task saved successfully: {}", savedTask);
        return savedTask;
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task with id {} not found", id);
                    return new TaskNotFoundException("Task with id " + id + " not found");
                });
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            log.warn("Attempt to delete non-existing task with id {}", id);
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
        log.info("Task with id {} deleted successfully", id);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));

        if (task.getTitle() != null) {
            existingTask.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getPriority() != null) {
            existingTask.setPriority(task.getPriority());
        }
        if (task.getCompleted() != null) {
            existingTask.setCompleted(task.getCompleted());
        }

        return taskRepository.save(existingTask);
    }
}
