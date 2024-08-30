package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;
    @Column(name = "created", nullable = false)
    LocalDateTime date = LocalDateTime.now();
    @Column(name = "description", nullable = false)
    String description;
    @OneToMany(mappedBy = "request")
    private List<Item> items = new ArrayList<>();
}
