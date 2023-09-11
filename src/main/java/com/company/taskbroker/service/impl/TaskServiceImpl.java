package com.company.taskbroker.service.impl;

import com.company.taskbroker.dto.TaskRequestDto;
import com.company.taskbroker.dto.TaskUpdateRequestDto;
import com.company.taskbroker.exception.CustomException;
import com.company.taskbroker.exception.NotFoundException;
import com.company.taskbroker.model.Status;
import com.company.taskbroker.model.Task;
import com.company.taskbroker.model.TaskStatus;
import com.company.taskbroker.model.User;
import com.company.taskbroker.repository.TaskRepository;
import com.company.taskbroker.service.TaskService;
import com.company.taskbroker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Task createTask(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        if (taskRequestDto.getTitle().isEmpty()) {
            throw new CustomException("The task was not created, the title is missing");
        }

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setTaskStatus(TaskStatus.READY);

        if (taskRequestDto.getUserId() != null) {
            User user = userService.findById(taskRequestDto.getUserId());
            task.setUser(user);
        }

        task.setStatus(Status.ACTIVE);
        task.setCreated(new Date());
        task.setUpdated(new Date());
        Task newTask = taskRepository.save(task);
        log.info("Task: {} successfully created", newTask.getTitle());
        return newTask;
    }

    @Override
    public Task findById(Long id) {
        Task task = taskRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new CustomException("Task not found"));
        log.info("Task with id {} founded", id);
        return task;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        Page<Task> tasks = taskRepository.findByStatus(Status.ACTIVE, pageable);
        log.info("Found {} tasks", tasks.getTotalElements());
        return tasks;
    }

    @Override
    public Task update(TaskUpdateRequestDto taskUpdateRequestDto) {
        Task task = taskRepository.findById(taskUpdateRequestDto.getId())
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (taskUpdateRequestDto.getDescription() != null
                && !taskUpdateRequestDto.getDescription().equals(task.getDescription())) {
            task.setDescription(taskUpdateRequestDto.getDescription());
            log.info("Updated Description field for task with id {}", taskUpdateRequestDto.getId());
        }
        if (taskUpdateRequestDto.getTaskStatus() != null
                && !taskUpdateRequestDto.getTaskStatus().equals(task.getTaskStatus())) {
            task.setTaskStatus(taskUpdateRequestDto.getTaskStatus());
            log.info("Updated Task Status field for task with id {}", taskUpdateRequestDto.getId());
        }
        if (taskUpdateRequestDto.getUser_id() != null
                && task.getUser() != null
                && !taskUpdateRequestDto.getUser_id().equals(task.getUser().getId())) {
            User user = userService.findById(taskUpdateRequestDto.getUser_id());
            if (user != null) {
                task.setUser(user);
                log.info("Updated User field for task with id {}", taskUpdateRequestDto.getId());
            }
        }
        task = taskRepository.save(task);
        log.info("Task with id {} updated", taskUpdateRequestDto.getId());
        return task;
    }

    @Override
    public void removeById(Long id) {
        Task task = taskRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        task.setStatus(Status.DELETED);
        taskRepository.save(task);
        log.info("Task with id {} removed", id);
    }

    @Override
    public void removeCompletelyById(Long id) {
        taskRepository.deleteById(id);
    }
}
