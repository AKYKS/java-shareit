package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userMapper.toUserDtoList(userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(userMapper.toUserDto(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userMapper.touser(createUserDto);
        return ResponseEntity.ok().body(userMapper.toUserDto(userService.createUser(user)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody CreateUserDto createUserDto,
                                           @PathVariable long id) {
        User user = userMapper.touser(createUserDto);
        user.setId(id);
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
