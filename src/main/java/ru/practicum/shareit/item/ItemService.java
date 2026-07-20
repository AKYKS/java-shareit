package ru.practicum.shareit.item;


import org.springframework.stereotype.Service;
import ru.practicum.shareit.expection.ConditionsNotMetException;
import ru.practicum.shareit.expection.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@Service
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemService(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    public Item getItemById(Long id) {
        return itemStorage.getItemById(id);
    }

    public Collection<Item> getAllUserItems(Long userId) {
        return itemStorage.getAllUserItems(userId);
    }

    public Item createItem(Item item, Long userId) {
        if (userId == null) {
            throw new ConditionsNotMetException("Отсутствует id пользователя");
        }

        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь с этим id отсутствует");
        }

        if (item.getAvailable() == null || item.getName().isBlank() || item.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Отсутствует одно из трех обязательных значений");
        }
        item.setOwner(userId);
        return itemStorage.createItem(item);
    }

    public Item updateItem(Item item, Long userId) {
        if (userId == null) {
            throw new ConditionsNotMetException("Отсутствует id пользователя");
        }

        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь с этим id отсутствует");
        }

        item.setOwner(userId);
        return itemStorage.updateItem(item);
    }

    public Collection<Item> searchItems(String searchString) {
        return itemStorage.searchItems(searchString);
    }
}
