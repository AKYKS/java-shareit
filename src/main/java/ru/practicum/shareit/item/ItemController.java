package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Collection<Item> getAllUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllUserItems(userId);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/search")
    public Collection<Item> searchItems(@RequestParam("text") String searchString) {
        return itemService.searchItems(searchString);
    }

    @PostMapping
    public Item createItem(@Valid @RequestBody ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = ItemMapper.mapToItem(itemDto);
        return itemService.createItem(item, userId);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@Valid @RequestBody ItemDto itemDto,
                           @PathVariable("id") long id,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = ItemMapper.mapToItem(itemDto);
        item.setId(id);
        return itemService.updateItem(item, userId);
    }
}
