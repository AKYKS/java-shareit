package ru.practicum.shareit.item.dto;

public record CreateItemDto(String name,
                            String description,
                            Boolean available,
                            Long owner,
                            Long request) {
}
