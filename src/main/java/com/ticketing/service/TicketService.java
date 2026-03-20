package com.ticketing.service;

import com.ticketing.Enums.Priority;
import com.ticketing.Enums.TicketStatus;
import com.ticketing.dto.TicketRequestDTO;
import com.ticketing.entity.Ticket;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ticketing.repository.TicketRepository;
import com.ticketing.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Ticket createTicket(TicketRequestDTO dto) {

        User assignedUser = userRepository.findById(dto.getAssignedToId())
                .orElseThrow();

        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow();

        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(Priority.valueOf(dto.getPriority()));
        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setAssignedTo(assignedUser);
        ticket.setCreatedBy(createdBy);

        return ticketRepository.save(ticket);
    }
}