package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {
    Item getItemById(Long id);

    Collection<Item> getAllUserItems(Long userId);

    Item createItem(Item item);

    Item updateItem(Item item);

    Collection<Item> searchItems(String searchString);

}
