package com.company.taskbroker.rest;

import com.company.taskbroker.exception.HttpInternalServerErrorException;
import com.company.taskbroker.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Admins endpoints")
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/admin/")
public class AdminRestControllerV1 {

    private final TaskService taskService;

    @ApiOperation(value = "Remove task by id", authorizations = {@Authorization(value = "Bearer Token")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task removed"),
            @ApiResponse(code = 403, message = "Access denied, authorization required"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "remove_task/{id}")
    public ResponseEntity<String> remove(@PathVariable(name = "id") Long id) {
        try {
            taskService.removeCompletelyById(id);
            return new ResponseEntity<>("Task removed", HttpStatus.OK);
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }
}