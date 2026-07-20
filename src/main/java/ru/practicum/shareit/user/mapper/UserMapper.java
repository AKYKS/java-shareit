package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toUserDto(User user);

    User touser(CreateUserDto createUserDto);

    Collection<UserDto> toUserDtoList(Collection<User> users);
}
