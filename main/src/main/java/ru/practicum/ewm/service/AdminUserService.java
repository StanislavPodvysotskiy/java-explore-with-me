package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.responseDto.UserDto;
import ru.practicum.ewm.dto.NewUserRequest;

import java.util.List;

public interface AdminUserService {

    List<UserDto> findAll(List<Integer> ids, Integer from, Integer size);

    UserDto save(NewUserRequest newUser);

    void delete(Integer userId);
}
