package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.responseDto.UserDto;
import ru.practicum.ewm.dto.responseDto.UserShortDto;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.dto.NewUserRequest;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User makeUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        return user;
    }

    public static UserDto makeUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }

    public static UserShortDto makeUserShortDto(User user) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(user.getId());
        userShortDto.setName(user.getName());
        return userShortDto;
    }

    public static List<UserDto> makeListUserDto(List<User> users) {
        return users.stream().map(UserMapper::makeUserDto).collect(Collectors.toList());
    }

    public static List<UserShortDto> makeListUserShortDto(List<User> users) {
        return users.stream().map(UserMapper::makeUserShortDto).collect(Collectors.toList());
    }
}
