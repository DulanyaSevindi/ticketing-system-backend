package com.ticketing.specification;

import com.ticketing.entity.Ticket;
import com.ticketing.Enums.TicketStatus;
import com.ticketing.Enums.Priority;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {

    public static Specification<Ticket> hasTitle(String title) {
        return (root, query, cb) ->
                title == null ? null :
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Ticket> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), TicketStatus.valueOf(status));
    }

    public static Specification<Ticket> hasPriority(String priority) {
        return (root, query, cb) ->
                priority == null ? null :
                        cb.equal(root.get("priority"), Priority.valueOf(priority));
    }

    public static Specification<Ticket> hasAssignedTo(Long userId) {
        return (root, query, cb) ->
                userId == null ? null :
                        cb.equal(root.get("assignedTo").get("id"), userId);
    }
}
