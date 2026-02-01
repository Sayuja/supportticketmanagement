package com.example.supportticketmanagement.repository;

import com.example.supportticketmanagement.dto.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findByCustomerId(int customerId);
}