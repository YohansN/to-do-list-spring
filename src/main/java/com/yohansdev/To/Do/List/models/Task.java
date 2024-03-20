package com.yohansdev.To.Do.List.models;

import com.yohansdev.To.Do.List.models.dtos.CreateTaskDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "tasks")
@Entity(name = "task")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date deadline;
    //private boolean isComplete; adicionar posteriormente caso pedido pelo cliente!

    public Task(CreateTaskDto taskDto){
        this.title = taskDto.title();
        this.description = taskDto.description();
        this.deadline = taskDto.deadline();
    }
}

