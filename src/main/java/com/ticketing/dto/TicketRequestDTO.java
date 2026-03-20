package com.ticketing.dto;

import lombok.Data;

@Data
public class TicketRequestDTO {
    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assignedToId;
    private Long createdById;
}
