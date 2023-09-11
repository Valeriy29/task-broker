package com.company.taskbroker.mapper;

import com.company.taskbroker.dto.UserRegistrationRequestDto;
import com.company.taskbroker.dto.UserRegistrationResponseDto;
import com.company.taskbroker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "updated", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "tasks", ignore = true)
    })
    User mapUserRegistrationRequestDtoToUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserRegistrationResponseDto mapUserToUserRegistrationResponseDto(User user);
}
