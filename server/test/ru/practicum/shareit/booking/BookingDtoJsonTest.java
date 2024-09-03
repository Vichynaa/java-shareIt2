package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ItemForBookingDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingDtoJsonTest {
    private final JacksonTester<BookingDto> json;

    @Test
    protected void testBookingDto() throws Exception {
        BookingDto bookingDto = getBookingDto();

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo("Item 1");
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.booker.name").isEqualTo("User");
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2024-10-01T15:30:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2024-10-30T15:30:00");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("APPROVED");
    }

    private static BookingDto getBookingDto() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 15, 30);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 15, 30);
        ItemForBookingDto item = new ItemForBookingDto();
        item.setId(1L);
        item.setName("Item 1");
        UserDto booker = new UserDto();
        booker.setId(2L);
        booker.setName("User");
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(startDate);
        bookingDto.setEnd(endDate);
        bookingDto.setStatus(BookingStatus.APPROVED);
        return bookingDto;
    }
}
