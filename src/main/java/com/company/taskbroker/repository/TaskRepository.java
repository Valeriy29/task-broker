package com.company.taskbroker.repository;

import com.company.taskbroker.model.Status;
import com.company.taskbroker.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(Status status, Pageable pageable);

    Optional<Task> findByIdAndStatus(Long id, Status status);

}
