package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<Collection<ItemDto>> getAllUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok().body(itemMapper.toItemDtoList(itemService.getAllUserItems(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable long id) {
        return ResponseEntity.ok().body(itemMapper.toItemDto(itemService.getItemById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<ItemDto>> searchItems(@RequestParam("text") String searchString) {
        return ResponseEntity.ok().body(itemMapper.toItemDtoList(itemService.searchItems(searchString)));
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody CreateItemDto createItemDto,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemMapper.toItem(createItemDto);
        return ResponseEntity.ok().body(itemMapper.toItemDto(itemService.createItem(item, userId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@Valid @RequestBody CreateItemDto createItemDto,
                                              @PathVariable long id,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemMapper.toItem(createItemDto);
        item.setId(id);
        return ResponseEntity.ok().body(itemMapper.toItemDto(itemService.updateItem(item, userId)));
    }
}
