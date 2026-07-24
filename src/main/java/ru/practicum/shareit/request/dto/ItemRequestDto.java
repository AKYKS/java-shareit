package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserBookerDto;

@Data
@Builder
public class ItemRequestDto {

    private Long id;
    private String description;
    private UserBookerDto requestor;

}
