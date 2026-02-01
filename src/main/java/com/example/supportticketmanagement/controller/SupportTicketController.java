package com.example.supportticketmanagement.controller;

import com.example.supportticketmanagement.dto.SupportTicket;
import com.example.supportticketmanagement.service.SupportTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supporttickets")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @GetMapping
    public List<SupportTicket> getAllTickets() {
        return supportTicketService.findAll();
    }

    @GetMapping("/{customerId}")
    public SupportTicket getTicketByCustomerId(@PathVariable int customerId) {
        return supportTicketService.findByCustomerId(customerId);
    }

    @PostMapping
    public SupportTicket createTicket(@RequestBody SupportTicket supportTicket) {
        return supportTicketService.create(supportTicket);
    }

    @PutMapping("/{id}")
    public SupportTicket updateTicket(@PathVariable Long id, @RequestBody SupportTicket supportTicket) {
        return supportTicketService.update(id, supportTicket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        supportTicketService.delete(id);
    }
}