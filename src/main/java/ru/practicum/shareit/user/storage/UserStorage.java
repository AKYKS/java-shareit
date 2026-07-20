package ru.practicum.shareit.user.storage;


import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserStorage {

    User getUserById(Long id);

    Collection<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);
}
