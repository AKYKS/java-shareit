package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    ItemDto toItemDto(Item item);

    Item toItem(CreateItemDto createItemDto);

    Collection<ItemDto> toItemDtoList(Collection<Item> items);
}
