package com.company.taskbroker.dto;

import lombok.Data;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private Long userId;
}
