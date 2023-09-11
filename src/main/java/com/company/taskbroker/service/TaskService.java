package com.company.taskbroker.service;

import com.company.taskbroker.dto.TaskRequestDto;
import com.company.taskbroker.dto.TaskUpdateRequestDto;
import com.company.taskbroker.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task createTask(TaskRequestDto taskRequestDto);

    Task findById(Long id);

    Page<Task> findAll(Pageable pageable);

    Task update(TaskUpdateRequestDto taskUpdateRequestDto);

    void removeById(Long id);

    void removeCompletelyById(Long id);
}
