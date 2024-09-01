package ru.practicum.shareit.item;

import exception.NotFoundException;
import exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.CommentRequest;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ItemDbService itemDbService;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    @Test
    protected void create_whenUserFound_thenReturnItem() {
        Long userId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("name");

        User user = new User();

        Item expectedItem = new Item();
        expectedItem.setName("name");
        expectedItem.setOwner(user);


        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.save(any(Item.class))).thenReturn(expectedItem);

        Item request = itemDbService.create(userId, itemRequest);

        Assertions.assertEquals(expectedItem, request);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(itemRepository).save(any(Item.class));
    }

    @Test
    protected void create_whenUserNotFound_thenReturnNotFoundException() {
        Long userId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("name");

        User user = new User();

        Item expectedItem = new Item();
        expectedItem.setName("name");
        expectedItem.setOwner(user);


        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.create(userId, itemRequest));

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).findById(userId);
        Mockito.verify(itemRepository, Mockito.never()).save(any(Item.class));
    }

    @Test
    protected void update_whenItemFound_thenReturnItem() {
        long userId = 1L;
        long itemId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("New name");

        Item oldItem = new Item();
        oldItem.setName("Old name");
        oldItem.setOwner(new User());

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(oldItem));
        Mockito.when(itemRepository.existsById(itemId)).thenReturn(true);
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(itemRepository.existsItemByOwnerId(itemId, userId)).thenReturn(true);

        itemDbService.update(userId, itemId, itemRequest);

        Mockito.verify(itemRepository).save(itemArgumentCaptor.capture());
        Item savedItem = itemArgumentCaptor.getValue();

        Assertions.assertEquals("New name", savedItem.getName());
        Mockito.verify(itemRepository).existsById(itemId);
        Mockito.verify(itemRepository).findById(itemId);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRepository).existsItemByOwnerId(itemId, userId);
        Mockito.verify(itemRepository).save(any(Item.class));
    }

    @Test
    protected void update_whenItemNotFound_thenReturnNotFoundException() {
        long userId = 1L;
        long itemId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("New name");
        Mockito.when(itemRepository.existsById(itemId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.update(userId, itemId, itemRequest));

        Mockito.verify(itemRepository).existsById(itemId);
        Mockito.verify(itemRepository, Mockito.never()).findById(itemId);
        Mockito.verify(userRepository, Mockito.never()).existsById(userId);
        Mockito.verify(itemRepository, Mockito.never()).existsItemByOwnerId(itemId, userId);
        Mockito.verify(itemRepository, Mockito.never()).save(any(Item.class));
    }

    @Test
    protected void update_whenUserNotFound_thenReturnNotFoundException() {
        long userId = 1L;
        long itemId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("New name");
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));
        Mockito.when(itemRepository.existsById(itemId)).thenReturn(true);
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.update(userId, itemId, itemRequest));

        Mockito.verify(itemRepository).existsById(itemId);
        Mockito.verify(itemRepository).findById(itemId);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRepository, Mockito.never()).existsItemByOwnerId(itemId, userId);
        Mockito.verify(itemRepository, Mockito.never()).save(any(Item.class));
    }

    @Test
    protected void update_whenUserIsNotOwner_thenReturnNotFoundException() {
        long userId = 1L;
        long itemId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("New name");

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));
        Mockito.when(itemRepository.existsById(itemId)).thenReturn(true);
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(itemRepository.existsItemByOwnerId(itemId, userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.update(userId, itemId, itemRequest));

        Mockito.verify(itemRepository).existsById(itemId);
        Mockito.verify(itemRepository).findById(itemId);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRepository).existsItemByOwnerId(itemId, userId);
        Mockito.verify(itemRepository, Mockito.never()).save(any(Item.class));
    }

    @Test
    protected void getItemsByUser_WhenUserFound_thenReturnListOfItems() {
        long userId = 1L;
        Item item = new Item();
        item.setDescription("description");

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(itemRepository.findByOwnerId(userId)).thenReturn(List.of(item));

        List<Item> items = itemDbService.getItemsByUser(userId);

        Assertions.assertEquals(item.getDescription(), items.getFirst().getDescription());
        Mockito.verify(itemRepository).findByOwnerId(userId);
    }

    @Test
    protected void getItemsByUser_WhenUserNotFound_thenReturnListOfItems() {
        Long userId = 1L;
        Item item = new Item();
        item.setDescription("description");

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.getItemsByUser(userId));
        Mockito.verify(itemRepository, Mockito.never()).findByOwnerId(userId);
    }

    @Test
    protected void getItemById_WhenUserFound_thenReturnListOfItems() {
        Long itemId = 1L;
        Item item = new Item();
        item.setDescription("description");

        Mockito.when(itemRepository.existsById(itemId)).thenReturn(true);
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Item request = itemDbService.getItemById(itemId);

        Assertions.assertEquals(item, request);
        Mockito.verify(itemRepository).findById(itemId);
    }

    @Test
    protected void getItemById_WhenItemNotFound_thenReturnListOfItems() {
        Long itemId = 1L;
        Mockito.when(itemRepository.existsById(itemId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemDbService.getItemById(itemId));
        Mockito.verify(itemRepository, Mockito.never()).findById(itemId);
    }

    @Test
    protected void search_whenTextIsNotBlank_thenReturnItems() {
        Long userId = 1L;
        String searchText = "item";
        Item item = new Item();
        item.setName("item name");
        List<Item> items = List.of(item);
        Mockito.when(itemRepository.findItemByText(searchText)).thenReturn(items);

        List<Item> request = itemDbService.search(Optional.of(searchText), userId);

        Assertions.assertEquals(items.getFirst().getName(), request.getFirst().getName());
        Mockito.verify(itemRepository).findItemByText(searchText);
    }

    @Test
    protected void search_whenTextIsBlank_thenReturnItems() {
        Long userId = 1L;
        String searchText = "";

        List<Item> request = itemDbService.search(Optional.of(searchText), userId);

        Assertions.assertEquals(new ArrayList<>(), request);
        Mockito.verify(itemRepository, Mockito.never()).findItemByText(searchText);
    }

    @Test
    protected void createComment_whenValidationIsCorrect_thenReturnComment() {
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("Text");
        Comment newComment = new Comment();
        newComment.setText("Text");
        Booking booking = new Booking();
        booking.setId(1L);
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.ru");

        Mockito.when(bookingRepository.findPastBooking(anyLong(), anyLong(), any(Timestamp.class))).thenReturn(booking);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(newComment);

        Comment comment = itemDbService.createComment(commentRequest, userId, itemId);

        Assertions.assertEquals(newComment.getText(), comment.getText());
        Mockito.verify(bookingRepository).findPastBooking(anyLong(), anyLong(), any(Timestamp.class));
    }

    @Test
    protected void createComment_whenValidationIsNotCorrect_thenReturnComment() {
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("Text");
        Comment newComment = new Comment();
        newComment.setText("Text");
        Booking booking = new Booking();
        booking.setId(1L);
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.ru");

        Mockito.when(bookingRepository.findPastBooking(anyLong(), anyLong(), any(Timestamp.class))).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> itemDbService.createComment(commentRequest, userId, itemId));
    }
}
