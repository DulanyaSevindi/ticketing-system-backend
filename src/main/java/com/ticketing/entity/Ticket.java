package com.ticketing.entity;

import com.ticketing.Enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.ticketing.Enums.Priority;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table (name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDateTime createdAt;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private User createdBy;


}
