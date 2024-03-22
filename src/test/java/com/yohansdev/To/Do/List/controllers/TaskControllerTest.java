package com.yohansdev.To.Do.List.controllers;

import com.yohansdev.To.Do.List.models.Task;
import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
import com.yohansdev.To.Do.List.models.dtos.TaskResponseDto;
import com.yohansdev.To.Do.List.models.dtos.UpdateTaskDto;
import com.yohansdev.To.Do.List.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;
    @Mock
    TaskService taskService;

    @BeforeEach
    void testSetup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("GetAllTasks should return a list of tasks.")
    void getAllTasks_ShouldReturnListOfTasks_WhenSuceed() {
        List<Task> tasks = Arrays.asList(
                new Task(1L, "Tarefa 1", "Descrição da Tarefa 1" , new Date(2054, 10, 24)),
                new Task(2L, "Tarefa 2", "Descrição da Tarefa 2", new Date(2032, 02, 13))
        );

        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> responseEntity = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tasks, responseEntity.getBody());
    }


    @Test
    @DisplayName("CreateTask should return CreateTaskDto when succeed.")
    void createTask_ShouldReturnCreateTaskDto_WhenSucceed() {

        CreateTaskDto createTaskDto = new CreateTaskDto("Tarefa 1", "Descrição da Tarefa 1", new Date(2054, 10, 24));
        TaskResponseDto taskResponseDto = new TaskResponseDto("Tarefa 1", "Descrição da Tarefa 1", new Date(2054, 10, 24));

        Mockito.when(taskService.createTask(createTaskDto)).thenReturn(taskResponseDto);
        ResponseEntity<TaskResponseDto> responseEntity = taskController.createTask(createTaskDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(taskResponseDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("UpdateTask should return task when succeed.")
    void updateTask_ShouldReturnTask_WhenSucceed() {

        UpdateTaskDto updateTaskDto = new UpdateTaskDto(1L, "Tarefa 1 atualizada", "Descrição da Tarefa 1 atualizada", new Date(2054, 10, 27));
        Task updateTaskResponse = new Task(1L, "Tarefa 1 atualizada", "Descrição da Tarefa 1 atualizada", new Date(2054, 10, 27));

        Mockito.when(taskService.updateTask(updateTaskDto)).thenReturn(updateTaskResponse);
        ResponseEntity<Task> actualUpdateTaskResponse = taskController.updateTask(updateTaskDto);

        assertEquals(HttpStatus.OK, actualUpdateTaskResponse.getStatusCode());
        assertEquals(updateTaskResponse, actualUpdateTaskResponse.getBody());
    }

    @Test
    void deleteTask_ShouldReturnOk_WhenSuceed() {
        var taskDeleteResponse = assertDoesNotThrow(() -> taskController.deleteTask(1L));
        assertEquals(HttpStatus.OK, taskDeleteResponse.getStatusCode());
    }
}