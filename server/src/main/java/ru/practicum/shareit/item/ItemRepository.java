package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i WHERE i.id = ?1 AND i.owner.id = ?2")
    Boolean existsItemByOwnerId(long itemId, long userId);

    List<Item> findByOwnerId(long ownerId);

    @Query("SELECT i FROM Item i WHERE (LOWER(i.name) LIKE CONCAT('%', ?1, '%') OR LOWER(i.description) LIKE CONCAT('%', ?1, '%')) AND i.available = true")
    List<Item> findItemByText(String text);
}
