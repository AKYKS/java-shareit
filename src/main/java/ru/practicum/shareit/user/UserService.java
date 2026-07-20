package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.expection.ConditionsNotMetException;
import ru.practicum.shareit.expection.DuplicatedDataException;
import ru.practicum.shareit.expection.NotFoundException;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUserById(Long id) {
        if (userStorage.getUserById(id) == null) {
            throw new NotFoundException("Такого юзера нет в списке!");
        }
        return userStorage.getUserById(id);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        checkUserData(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        if (user.getEmail() != null) {
            checkUserData(user);
        }
        return userStorage.updateUser(user);
    }


    private void checkUserData(User user) {
        final Map<Long, User> users = userStorage.getAllUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ConditionsNotMetException("Электронная почта не может быть пустой и должна содержать символ \"@\"");
        }
        if (users.values().stream()
                .anyMatch(curUser -> curUser.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
    }

    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }
}
