package com.yohansdev.To.Do.List.services;

import com.yohansdev.To.Do.List.models.Task;
import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
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
    public boolean createTask(@Valid CreateTaskDto taskDto){
        Task newTask = new Task(taskDto);
        taskRepository.save(newTask);
        return true;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAllByOrderByDeadlineAsc();
    }

    @Transactional
    public Optional<Task> updateTask(@Valid UpdateTaskDto taskDto){
        Optional<Task> taskOptional = taskRepository.findById(taskDto.id());
        if(taskOptional.isEmpty())
            return Optional.empty();

        Task task = taskOptional.get();

        //Verificação para atualizar os valores caso estes sejam passados no parametro.
        if(!taskDto.title().isBlank() && !taskDto.title().isEmpty())
            task.setTitle(taskDto.title());
        if(!taskDto.description().isBlank() && !taskDto.description().isEmpty())
            task.setDescription(taskDto.description());
        if(taskDto.deadline() != null)
            task.setDeadline(taskDto.deadline());

        return Optional.of(task);
    }

    @Transactional
    public boolean deleteTask(Long taskId){
        Optional<Task> taskToBeDeletedOptional = taskRepository.findById(taskId);
        if (taskToBeDeletedOptional.isEmpty())
            return false;

        Task taskToBeDeleted = taskToBeDeletedOptional.get();
        taskRepository.delete(taskToBeDeleted);
        return true;
    }
}
