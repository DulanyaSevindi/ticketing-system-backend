package com.ticketing.controller;

import com.ticketing.dto.ticket.TicketFilterDTO;
import com.ticketing.dto.ticket.TicketRequestDTO;
import com.ticketing.dto.ticket.TicketStatusDTO;
import com.ticketing.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketing.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    //create
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.createTicket(dto));
    }

    //getAll
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Ticket>> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            TicketFilterDTO filter
    ) {
        return ResponseEntity.ok(
                ticketService.getTickets(page, size, sortBy, sortDir, filter)
        );
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    // Update Ticket
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.updateTicket(id, dto));
    }

    //delete ticket
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ("successfully deleted");
    }

    //assign ticket
    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity <Ticket> assignTicket(@PathVariable Long id, @PathVariable Long userId){
        return ResponseEntity.ok(ticketService.assignTicket(id,userId));
    }

    //update ticket status
    @PutMapping("/{id}/status")
    public Ticket updateStatus(@PathVariable Long id,
                               @RequestBody TicketStatusDTO dto) {
        return ticketService.updateStatus(id, dto);
    }
}
