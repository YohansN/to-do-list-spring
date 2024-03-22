package com.yohansdev.To.Do.List.services;

import com.yohansdev.To.Do.List.infra.exceptions.DeleteTaskException;
import com.yohansdev.To.Do.List.infra.exceptions.GetTasksException;
import com.yohansdev.To.Do.List.infra.exceptions.TaskCreationException;
import com.yohansdev.To.Do.List.infra.exceptions.TaskNotFoundException;
import com.yohansdev.To.Do.List.models.Task;
import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
import com.yohansdev.To.Do.List.models.dtos.TaskResponseDto;
import com.yohansdev.To.Do.List.models.dtos.UpdateTaskDto;
import com.yohansdev.To.Do.List.repositories.TaskRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskServiceTest {

    @InjectMocks
    TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    private Task task;
    private Validator validator;

    @BeforeEach
    void testSetup(){
        MockitoAnnotations.initMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); //Ativa as anotações validations.
        this.validator = factory.getValidator();
        this.task = new Task(1L,"Título", "Descrição", new Date(2062, 02, 23));
    }

    //CreateTaskTest
    @Test
    @DisplayName("Should return TaskResponseDto when valid")
    void createTask_Should_ReturnTaskResponseDto_When_CreateTaskDtoIsValid() {

        CreateTaskDto taskDto = new CreateTaskDto("Título da Tarefa", "Descrição da Tarefa", new Date(2030, 10, 03));
        Task newTask = new Task(taskDto);
        TaskResponseDto expectedResponseDto = new TaskResponseDto(newTask.getTitle(), newTask.getDescription(), newTask.getDeadline());

        when(taskRepository.save(newTask)).thenReturn(null);
        TaskResponseDto actualResponseDto = taskService.createTask(taskDto);

        Assertions.assertThat(actualResponseDto).isNotNull();
        Assertions.assertThat(actualResponseDto.title()).isEqualTo(expectedResponseDto.title());
        Assertions.assertThat(actualResponseDto.description()).isEqualTo(expectedResponseDto.description());
        Assertions.assertThat(actualResponseDto.deadline()).isEqualTo(expectedResponseDto.deadline());
    }

    @Test
    @DisplayName("Should return exception when CreateTask is invalid.")
    void createTask_ShouldThrowException_When_CreateTaskDtoIsInvalid() {

        String titleErrorMessage = "O título é obrigatório.";
        String descriptionErrorMessage = "A descrição é obrigatória.";
        String deadlineErrorMessage = "O prazo não pode ser anterior a data de hoje.";
        CreateTaskDto taskDto = new CreateTaskDto("", "", new Date(1970, 02, 23));
        Set<ConstraintViolation<CreateTaskDto>> violations = validator.validate(taskDto);

        assertFalse(violations.isEmpty());
        for (ConstraintViolation<CreateTaskDto> violation : violations) {
            if (violation.getPropertyPath().toString().equals("title")) {
                assertEquals(titleErrorMessage, violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("description")) {
                assertEquals(descriptionErrorMessage, violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("deadline")) {
                assertEquals(deadlineErrorMessage, violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Should return exception when CreateTask fail.")
    void createTask_ShouldThrowException_When_CreateTaskDtoFail() {

        String errorMessage = "Falha ao criar tarefa.";
        CreateTaskDto taskDto = new CreateTaskDto("Título da Tarefa", "Descrição da Tarefa", new Date());
        Task task = new Task(taskDto);

        Mockito.doThrow(new TaskCreationException()).when(taskRepository).save(task);

        final TaskCreationException ex = assertThrows(TaskCreationException.class, () -> {
            taskService.createTask(taskDto);
        });

        Assertions.assertThat(ex);
        assertEquals(errorMessage, ex.getMessage());
    }


    //GetAllTasksTest
    @Test
    @DisplayName("Should return list of Tasks.")
    void getAllTasks_ShouldReturnListOfTasks(){

        when(taskRepository.findAllByOrderByDeadlineAsc()).thenReturn(Collections.singletonList(task));
        List<Task> tasksList = taskService.getAllTasks();

        assertEquals(Collections.singletonList(task), tasksList);
        verify(taskRepository).findAllByOrderByDeadlineAsc();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    @DisplayName("Should return GetTasksException when fail.")
    void getAllTasks_ShouldReturnGetTasksException_WhenFails(){

        String errorMessage = "Falha ao buscar tarefas.";

        Mockito.doThrow(new GetTasksException()).when(taskRepository).findAllByOrderByDeadlineAsc();
        final GetTasksException ex = assertThrows(GetTasksException.class, () -> {
            taskService.getAllTasks();
        });

        Assertions.assertThat(ex);
        assertEquals(errorMessage, ex.getMessage());
    }

    //UpdateTaskTest
    @Test
    @DisplayName("Should return updated task when task is found.")
    void updateTask_ShouldReturnUpdatedTask_WhenTaskIsFound() {

        UpdateTaskDto taskDto = new UpdateTaskDto(1L, "Título atualizado", "Descrição atualizada", new Date(2040, 12, 23));

        when(taskRepository.findById(taskDto.id())).thenReturn(Optional.of(task));
        Task expectedTaskResponse = Optional.ofNullable(task).orElse(null);
        Task actualTaskResponse = taskService.updateTask(taskDto);

        assertEquals(expectedTaskResponse, actualTaskResponse);
    }

    @Test
    @DisplayName("Should return exception when task is not found")
    void updateTask_ShouldReturnTaskNotFoundException_WhenTaskIsNotFound() {

        String errorMessage = "Tarefa não encontrada";
        UpdateTaskDto updateTaskDto = new UpdateTaskDto(999L, "Título atualizado", "Descrição atualizada", new Date(2040, 12, 23));

        when(taskRepository.findById(updateTaskDto.id())).thenReturn(Optional.of(task));
        doThrow(new TaskNotFoundException()).when(taskRepository).findById(updateTaskDto.id());
        final TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(updateTaskDto);
        });

        Assertions.assertThat(ex);
        assertEquals(ex.getMessage(), errorMessage);
    }

    //DeleteTaskTest
    @Test
    @DisplayName("Should return void when succeed")
    void deleteTask_ShouldReturnVoid_WhenSucceed() {

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Task taskFound = Optional.ofNullable(task).orElse(null);
        taskService.deleteTask(taskFound.getId());

        verify(taskRepository).delete(task);
    }

    @Test
    @DisplayName("Should return exception when is not found.")
    void deleteTask_ShouldReturnTaskNotFoundException_WhenTaskIdIsNotFound() {

        String errorMessage = "Tarefa não encontrada";

        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());
        Mockito.doThrow(new TaskNotFoundException()).when(taskRepository).findById(null);
        final TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(task.getId());
        });

        Assertions.assertThat(ex);
        assertEquals(errorMessage, ex.getMessage());
    }

    @Test
    @DisplayName("Should return exception when delete method fail.")
    void deleteTask_ShouldReturnDeleteTaskException_WhenFail() {

        String errorMessage = "Falha ao excluir tarefa.";

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        doThrow(new DeleteTaskException()).when(taskRepository).delete(task);
        final DeleteTaskException ex = assertThrows(DeleteTaskException.class, () -> {
            taskService.deleteTask(task.getId());
        });

        Assertions.assertThat(ex);
        assertEquals(errorMessage, ex.getMessage());
    }
}