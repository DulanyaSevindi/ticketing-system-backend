package com.ticketing.dto.ticket;

import lombok.Data;

@Data
public class TicketFilterDTO {

    private String title;
    private String status;
    private String priority;
    private Long assignedToId;

}
