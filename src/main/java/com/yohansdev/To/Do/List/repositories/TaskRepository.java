package com.yohansdev.To.Do.List.repositories;

import com.yohansdev.To.Do.List.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByDeadlineAsc();
}
