package com.yohansdev.To.Do.List.controllers;

import com.yohansdev.To.Do.List.models.Task;
import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
import com.yohansdev.To.Do.List.models.dtos.TaskResponseDto;
import com.yohansdev.To.Do.List.models.dtos.UpdateTaskDto;
import com.yohansdev.To.Do.List.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("todo/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping
    @Operation(summary = "Lista todas as tarefas cadastradas.", method = "GET")
    public ResponseEntity getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    @Operation(summary = "Realiza o cadastro de uma nova tarefa.", method = "POST")
    public ResponseEntity createTask(@Valid @RequestBody CreateTaskDto createTaskDto){
        TaskResponseDto taskCreated = taskService.createTask(createTaskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskDto);
    }

    @PutMapping
    @Operation(summary = "Atualiza as tarefas previamente cadastradas.", method = "PUT")
    public ResponseEntity updateTask(@Valid @RequestBody UpdateTaskDto updateTaskDto){
        Task task = taskService.updateTask(updateTaskDto);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Apagar uma tarefa.", method = "DELETE")
    public ResponseEntity deleteTask(@PathVariable @NotNull(message = "O ID não pode ser nulo.")
                                         @Positive(message = "O ID deve ser positivo.") Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Tarefa excluída com sucesso.");
    }
}
