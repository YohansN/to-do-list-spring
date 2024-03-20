package com.yohansdev.To.Do.List.controllers;

import com.yohansdev.To.Do.List.models.Task;
import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
import com.yohansdev.To.Do.List.models.dtos.UpdateTaskDto;
import com.yohansdev.To.Do.List.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity createTask(@Valid @RequestBody CreateTaskDto createTaskDto){
        if(taskService.createTask(createTaskDto))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity updateTask(@Valid @RequestBody UpdateTaskDto updateTaskDto){
        var taskOp = taskService.updateTask(updateTaskDto);
        if(taskOp.isPresent())
            return ResponseEntity.ok(taskOp);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        if(taskService.deleteTask(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
