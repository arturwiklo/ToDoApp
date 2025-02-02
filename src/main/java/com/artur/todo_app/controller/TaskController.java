package com.artur.todo_app.controller;

import com.artur.todo_app.dto.TaskRequestDto;
import com.artur.todo_app.model.Task;
import com.artur.todo_app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/getTasks")
    public List<Task> getTask() {
        return taskService.findAllTasks();
    }

    @GetMapping("/getTask/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.findTaskById(id);
    }

    @PostMapping("/postTask")
    public Task postTask(@Valid @RequestBody TaskRequestDto task) {
        return taskService.saveTask(task);
    }

    @PutMapping("/putTask/{id}")
    public Task putTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/deleteTask/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
