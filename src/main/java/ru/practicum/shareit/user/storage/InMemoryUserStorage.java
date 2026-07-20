package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.expection.NotFoundException;
import ru.practicum.shareit.expection.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public User createUser(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан!");
        }
        if (users.containsKey(user.getId())) {
            user.setEmail(user.getEmail());
            user.setName(user.getName());
            return user;
        } else throw new NotFoundException("Такого пользователя нет в списке!");
    }

    private Long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }
}
