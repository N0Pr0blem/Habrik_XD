package com.example.user_service.mapper;

import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.mapper.base.Mappable;
import com.example.user_service.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<UserEntity, UserResponseDto> {
}
