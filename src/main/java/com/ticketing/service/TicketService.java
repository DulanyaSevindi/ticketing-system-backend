package com.ticketing.service;

import com.ticketing.Enums.Priority;
import com.ticketing.Enums.TicketStatus;
import com.ticketing.dto.ticket.TicketFilterDTO;
import com.ticketing.dto.ticket.TicketRequestDTO;
import com.ticketing.dto.ticket.TicketStatusDTO;
import com.ticketing.entity.Ticket;
import com.ticketing.entity.User;
import jakarta.transaction.Transactional;
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
    @Transactional
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

        return ticketRepository.findByDeletedFalse(spec, pageable);
    }

    //create
    @Transactional
    public Ticket createTicket(TicketRequestDTO dto) {

        // Ensure createdById is present
        if (dto.getCreatedById() == null) {
            throw new RuntimeException("CreatedById cannot be null");
        }
        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        // Assigned user is optional
        User assignedUser = null;
        if (dto.getAssignedToId() != null) {
            assignedUser = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(Priority.valueOf(dto.getPriority()));
        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setDeleted(false);
        ticket.setAssignedTo(assignedUser);
        ticket.setCreatedBy(createdBy);

        return ticketRepository.save(ticket);
    }

    //get all
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAllByDeletedFalse();
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
        User user = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(Priority.valueOf(dto.getPriority()));
        ticket.setStatus(TicketStatus.valueOf(dto.getStatus()));
        ticket.setAssignedTo(user);

        return ticketRepository.save(ticket);
    }


    //delete
    @Transactional
    public void deleteTicket(Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setDeleted(true);

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

        if (dto.getFeedback() != null) {
            ticket.setFeedback(dto.getFeedback()); // ✅ save feedback
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }
}