package com.example.supportticketmanagement.exception;

public class SupportTicketNotFoundException extends  RuntimeException {

    public SupportTicketNotFoundException(Long id) {
        super("Could not find ticket " + id);
    }
}
