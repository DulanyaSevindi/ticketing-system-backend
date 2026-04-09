package com.ticketing.dto.ticket;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {
    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assignedToId;
    private Long createdById;
}
