package com.company.taskbroker.dto;

import com.company.taskbroker.model.TaskStatus;
import lombok.Data;

@Data
public class TaskResponseDto {
    private Long taskId;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private Long userId;
}
