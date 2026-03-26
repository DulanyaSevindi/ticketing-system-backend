package com.ticketing.service;

import com.ticketing.Enums.Priority;
import com.ticketing.Enums.TicketStatus;
import com.ticketing.dto.TicketFilterDTO;
import com.ticketing.dto.TicketRequestDTO;
import com.ticketing.dto.TicketStatusDTO;
import com.ticketing.entity.Ticket;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ticketing.repository.TicketRepository;
import com.ticketing.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ticketing.specification.TicketSpecification.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    //GET THE  PAGINATION,SEARCH,FILTER
    public Page<Ticket> getTickets(
            int page,
            int size,
            String sortBy,
            String sortDir,
            TicketFilterDTO filter
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Ticket> spec = Specification
                .where(hasTitle(filter.getTitle()))
                .and(hasStatus(filter.getStatus()))
                .and(hasPriority(filter.getPriority()))
                .and(hasAssignedTo(filter.getAssignedToId()));

        return ticketRepository.findAll(spec, pageable);
    }

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

    //get all
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // ✅ GET BY ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    //update the tickets
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

    //update the status
    public Ticket updateStatus(Long id, TicketStatusDTO dto) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));

        return ticketRepository.save(ticket);
    }
}