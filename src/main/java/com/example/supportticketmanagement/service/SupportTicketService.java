package com.example.supportticketmanagement.service;

import com.example.supportticketmanagement.dto.SupportTicket;
import com.example.supportticketmanagement.exception.SupportTicketNotFoundException;
import com.example.supportticketmanagement.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportTicketService {

    private final SupportTicketRepository repository;

    public SupportTicketService(SupportTicketRepository repository) {
        this.repository = repository;
    }

    public List<SupportTicket> findAll() {
        return repository.findAll();
    }

    public SupportTicket findByCustomerId(int customerId) {
        return repository.findByCustomerId(customerId)
                .orElseThrow(() -> new SupportTicketNotFoundException((long) customerId));
    }

    public SupportTicket create(SupportTicket supportTicket) {
        return repository.save(supportTicket);
    }

    public SupportTicket update(Long id, SupportTicket updatedTicket) {
        return repository.findById(id)
                .map(existingTicket -> {
                    existingTicket.setCatagory(updatedTicket.getCatagory());
                    existingTicket.setAssignedTo(updatedTicket.getAssignedTo());
                    existingTicket.setStatus(updatedTicket.getStatus());
                    existingTicket.setPriority(updatedTicket.getPriority());
                    existingTicket.setCustomerId(updatedTicket.getCustomerId());
                    return repository.save(existingTicket);
                })
                .orElseGet(() -> repository.save(updatedTicket));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
