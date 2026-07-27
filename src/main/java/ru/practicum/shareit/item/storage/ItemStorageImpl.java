package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Component
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item getItemById(Long id) {
        return items.get(id);
    }

    @Override
    public Collection<Item> getAllUserItems(Long userId) {
        return items.values().stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .toList();
    }

    @Override
    public Item createItem(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        if (item.getId() == null) {
            throw new ValidationException("Id должен быть указан!");
        }
        if (items.containsKey(item.getId())) {
            item.setName(item.getName());
            item.setAvailable(item.getAvailable());
            item.setDescription(item.getDescription());
            return item;
        } else throw new NotFoundException("Такого пользователя нет в списке!");
    }

    @Override
    public Collection<Item> searchItems(String searchString) {
        if (searchString == null || searchString.trim().isEmpty()) {
            return List.of(); // Возвращаем пустой список для пустой строки
        }

        String lowerSearchString = searchString.toLowerCase().trim();

        return items.values().stream()
                .filter(item -> item.getAvailable() == true) // Фильтруем доступные для аренды
                .filter(item -> matchesSearchCriteria(item, lowerSearchString)) // Фильтруем по поиску
                .toList();
    }

    private boolean matchesSearchCriteria(Item item, String searchString) {
        String itemName = item.getName() != null ? item.getName().toLowerCase() : "";
        String itemDescription = item.getDescription() != null
                ? item.getDescription().toLowerCase()
                : "";

        return itemName.contains(searchString) || itemDescription.contains(searchString);
    }

    private Long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
