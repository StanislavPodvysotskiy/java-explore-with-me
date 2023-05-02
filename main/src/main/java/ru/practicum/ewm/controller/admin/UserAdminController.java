package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.UserDto;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.service.AdminUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserAdminController {

    private final AdminUserService adminUserService;

    @GetMapping
    public List<UserDto> findAll(@RequestParam (required = false) List<Integer> ids,
                                 @RequestParam (defaultValue = "0") @PositiveOrZero Integer from,
                                 @RequestParam (defaultValue = "10") @Positive Integer size,
                                 HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        if (ids == null) {
            return adminUserService.findAll(from, size);
        }
        return adminUserService.findAllByIds(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody @Valid NewUserRequest newUser, HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return adminUserService.save(newUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Integer userId, HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        adminUserService.delete(userId);
    }
}
