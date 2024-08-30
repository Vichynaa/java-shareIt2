package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.CommentDto;

import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentDtoJsonTest {
    private final JacksonTester<CommentDto> json;

    @Test
    void testCommentDto() throws Exception {
        CommentDto comment = new CommentDto();
        comment.setId(1L);
        comment.setText("item");
        comment.setAuthorName("User");
        comment.setItemId(1L);
        comment.setCreated(LocalDateTime.of(2024, 8, 30, 15, 30));

        JsonContent<CommentDto> result = json.write(comment);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("User");
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2024-08-30T15:30:00");
    }
}
