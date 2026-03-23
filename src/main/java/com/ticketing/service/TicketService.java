package com.ticketing.service;

import com.ticketing.Enums.Priority;
import com.ticketing.Enums.TicketStatus;
import com.ticketing.dto.TicketRequestDTO;
import com.ticketing.dto.TicketStatusDTO;
import com.ticketing.entity.Ticket;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ticketing.repository.TicketRepository;
import com.ticketing.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;


    //create
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

    //getall
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // ✅ GET BY ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    //update
    public Ticket updateTicket(Long id,TicketRequestDTO dto){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(Priority.valueOf(dto.getPriority()));
        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));

        return ticketRepository.save(ticket);
    }


    //delete
    public void deleteTicket(Long id){
        ticketRepository.deleteById(id);
    }

    //assign ticket
    public Ticket assignTicket(Long id,Long userId){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setAssignedTo(user);

        return ticketRepository.save(ticket);
    }

    public Ticket updateStatus(Long id, TicketStatusDTO dto) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));

        return ticketRepository.save(ticket);
    }
}