package com.ticketing.dto;

import lombok.Data;

@Data
public class TicketFilterDTO {

    private String title;
    private String status;
    private String priority;
    private Long assignedToId;

}
