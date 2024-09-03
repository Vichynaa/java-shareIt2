package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.ItemInfo;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestDtoJsonTest {
    private final JacksonTester<ItemRequestDto> json;

    @Test
    protected void testItemRequestDto() throws Exception {
        LocalDateTime createdDate = LocalDateTime.of(2024, 8, 30, 15, 30);
        ItemInfo item1 = new ItemInfo();
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setOwnerId(1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setOwnerId(2L);
        itemRequestDto.setDescription("Request for items");
        itemRequestDto.setCreated(createdDate);
        itemRequestDto.setItems(List.of(item1));

        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.ownerId").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Request for items");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2024-08-30T15:30:00");
        assertThat(result).extractingJsonPathNumberValue("$.items[0].id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.items[0].name").isEqualTo("Item 1");
        assertThat(result).extractingJsonPathNumberValue("$.items[0].ownerId").isEqualTo(1);
    }
}
