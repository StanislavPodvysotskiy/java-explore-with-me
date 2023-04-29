package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.responseDto.UserDto;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.service.AdminUserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll(List<Integer> ids, Integer from, Integer size) {
        return UserMapper.makeListUserDto(userRepository.findAllIn(ids, PageRequest.of(from, size)).getContent());
    }

    @Override
    @Transactional
    public UserDto save(NewUserRequest newUser) {
        return UserMapper.makeUserDto(userRepository.save(UserMapper.makeUser(newUser)));
    }

    @Override
    @Transactional
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }
}
