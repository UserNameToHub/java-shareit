package ru.practicum.shareit.request.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requestor;

    @Column
    private String description;

    @Column
    private LocalDateTime created;
}