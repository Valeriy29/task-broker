package com.company.taskbroker.dto;

import com.company.taskbroker.model.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateRequestDto {
    private Long id;
    private String description;
    private TaskStatus taskStatus;
    private Long user_id;
}
