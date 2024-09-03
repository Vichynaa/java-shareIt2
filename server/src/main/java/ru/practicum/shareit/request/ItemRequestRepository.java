package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query("SELECT i FROM ItemRequest i WHERE i.owner.id = ?1 ORDER BY i.date DESC")
    List<ItemRequest> findByOwnerId(long ownerId);

    @Query("SELECT i FROM ItemRequest i WHERE i.owner.id != ?1 ORDER BY i.date DESC")
    List<ItemRequest> findAllByOthersOwners(long ownerId);
}
