package com.company.taskbroker.rest;

import com.company.taskbroker.dto.TaskRequestDto;
import com.company.taskbroker.dto.TaskResponseDto;
import com.company.taskbroker.dto.TaskUpdateRequestDto;
import com.company.taskbroker.exception.CreatingTaskException;
import com.company.taskbroker.exception.CustomException;
import com.company.taskbroker.exception.HttpInternalServerErrorException;
import com.company.taskbroker.exception.NotFoundException;
import com.company.taskbroker.mapper.TaskMapper;
import com.company.taskbroker.model.Task;
import com.company.taskbroker.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Task endpoints")
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/tasks/")
public class TaskRestControllerV1 {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @ApiOperation(value = "Create task", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task created"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto requestDto) {
        try {
            Task newTask = taskService.createTask(requestDto);
            TaskResponseDto responseDto = taskMapper.mapTaskToTaskResponseDto(newTask);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (CustomException e) {
            throw new CreatingTaskException(e.getMessage());
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }

    @ApiOperation(value = "Find all tasks", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks found"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("all")
    public ResponseEntity<List<TaskResponseDto>> findAll(Pageable pageable) {
        try {
            Page<Task> tasks = taskService.findAll(pageable);
            List<TaskResponseDto> responseDtoTasks = tasks.get().map(taskMapper::mapTaskToTaskResponseDto).toList();
            return new ResponseEntity<>(responseDtoTasks, HttpStatus.OK);
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }

    @ApiOperation(value = "Find task by id", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task found"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "find_task/{id}")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable(name = "id") Long id) {
        try {
            Task task = taskService.findById(id);
            TaskResponseDto responseDto = taskMapper.mapTaskToTaskResponseDto(task);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (CustomException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }

    @ApiOperation(value = "Update task", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task updated"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("update")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskUpdateRequestDto requestDto) {
        try {
            Task task = taskService.update(requestDto);
            TaskResponseDto responseDto = taskMapper.mapTaskToTaskResponseDto(task);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }

    @ApiOperation(value = "Remove task by id", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task removed"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "remove_task/{id}")
    public ResponseEntity<String> remove(@PathVariable(name = "id") Long id) {
        try {
            taskService.removeById(id);
            return new ResponseEntity<>("Task removed", HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }
}
