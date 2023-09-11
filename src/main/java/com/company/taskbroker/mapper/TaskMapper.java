package com.company.taskbroker.mapper;

import com.company.taskbroker.dto.TaskResponseDto;
import com.company.taskbroker.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(source = "id", target = "taskId"),
            @Mapping(source = "user.id", target = "userId"),
    })
    TaskResponseDto mapTaskToTaskResponseDto(Task task);
}
