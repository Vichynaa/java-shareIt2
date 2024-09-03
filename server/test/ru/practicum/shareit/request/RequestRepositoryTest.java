package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RequestRepositoryTest {
    @Autowired
    private ItemRequestRepository itemRequestRepository;
}
