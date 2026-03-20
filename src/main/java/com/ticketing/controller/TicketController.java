package com.ticketing.controller;

import com.ticketing.dto.TicketRequestDTO;
import com.ticketing.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketing.service.TicketService;

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



}
