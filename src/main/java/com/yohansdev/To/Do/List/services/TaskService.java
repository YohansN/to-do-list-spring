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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskResponseDto createTask(@Valid CreateTaskDto taskDto){
        Task newTask = new Task(taskDto);
        try {
            taskRepository.save(newTask);
        }catch (Exception exception){
            throw new TaskCreationException();
        }
        return new TaskResponseDto(newTask.getTitle(), newTask.getDescription(), newTask.getDeadline());
    }

    public List<Task> getAllTasks(){
        try {
            return taskRepository.findAllByOrderByDeadlineAsc();
        }catch (Exception exception){
            throw new GetTasksException();
        }
    }

    @Transactional
    public Task updateTask(@Valid UpdateTaskDto taskDto){
        Optional<Task> taskOptional = taskRepository.findById(taskDto.id());
        if(taskOptional.isEmpty())
            throw new TaskNotFoundException();

        Task task = taskOptional.get();

        //Verificação para atualizar os valores caso estes sejam passados como parametro.
        if(!taskDto.title().isBlank() && !taskDto.title().isEmpty())
            task.setTitle(taskDto.title());
        if(!taskDto.description().isBlank() && !taskDto.description().isEmpty())
            task.setDescription(taskDto.description());
        if(taskDto.deadline() != null)
            task.setDeadline(taskDto.deadline());

        return task;
    }

    @Transactional
    public void deleteTask(Long taskId){
        Optional<Task> taskToBeDeletedOptional = taskRepository.findById(taskId);
        if (taskToBeDeletedOptional.isEmpty()) throw new TaskNotFoundException();

        Task taskToBeDeleted = taskToBeDeletedOptional.get();

        try{
            taskRepository.delete(taskToBeDeleted);
        }catch (Exception ex){
            throw new DeleteTaskException();
        }
    }
}
