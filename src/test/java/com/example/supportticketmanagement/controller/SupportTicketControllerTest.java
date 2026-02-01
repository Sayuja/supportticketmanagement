package com.example.supportticketmanagement.controller;

import com.example.supportticketmanagement.dto.Priority;
import com.example.supportticketmanagement.dto.Status;
import com.example.supportticketmanagement.dto.SupportTicket;
import com.example.supportticketmanagement.repository.SupportTicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupportTicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupportTicketRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    // ==================== POST Tests ====================

    @Test
    @DisplayName("POST /supporttickets - Should create a new support ticket")
    void shouldCreateNewSupportTicket() throws Exception {
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomerId(123);
        ticket.setCatagory("Technical Support");
        ticket.setAssignedTo("John Doe");
        ticket.setStatus(Status.OPEN);
        ticket.setPriority(Priority.HIGH);

        mockMvc.perform(post("/supporttickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.customerId", is(123)))
                .andExpect(jsonPath("$.catagory", is("Technical Support")))
                .andExpect(jsonPath("$.assignedTo", is("John Doe")))
                .andExpect(jsonPath("$.status", is("OPEN")))
                .andExpect(jsonPath("$.priority", is("HIGH")));
    }

    @Test
    @DisplayName("POST /supporttickets - Should create ticket with MEDIUM priority")
    void shouldCreateTicketWithMediumPriority() throws Exception {
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomerId(456);
        ticket.setCatagory("Billing");
        ticket.setAssignedTo("Jane Smith");
        ticket.setStatus(Status.IN_PROGRESS);
        ticket.setPriority(Priority.MEDIUM);

        mockMvc.perform(post("/supporttickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priority", is("MEDIUM")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")));
    }

    // ==================== GET All Tests ====================

    @Test
    @DisplayName("GET /supporttickets - Should return empty list when no tickets exist")
    void shouldReturnEmptyListWhenNoTickets() throws Exception {
        mockMvc.perform(get("/supporttickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /supporttickets - Should return all tickets")
    void shouldReturnAllTickets() throws Exception {
        // Create two tickets
        createAndSaveTicket(101, "Network Issue", "Alice", Status.OPEN, Priority.HIGH);
        createAndSaveTicket(102, "Login Problem", "Bob", Status.RESOLVED, Priority.LOW);

        mockMvc.perform(get("/supporttickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].customerId", containsInAnyOrder(101, 102)));
    }

    // ==================== GET by Customer ID Tests ====================

    @Test
    @DisplayName("GET /supporttickets/{customerId} - Should return ticket by customer ID")
    void shouldReturnTicketByCustomerId() throws Exception {
        SupportTicket savedTicket = createAndSaveTicket(200, "Email Issue", "Charlie", Status.OPEN, Priority.MEDIUM);

        mockMvc.perform(get("/supporttickets/" + savedTicket.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(200)))
                .andExpect(jsonPath("$.catagory", is("Email Issue")));
    }

    @Test
    @DisplayName("GET /supporttickets/{customerId} - Should return 404 for non-existent customer")
    void shouldReturn404ForNonExistentCustomer() throws Exception {
        mockMvc.perform(get("/supporttickets/99999"))
                .andExpect(status().isNotFound());
    }

    // ==================== PUT Tests ====================

    @Test
    @DisplayName("PUT /supporttickets/{id} - Should update existing ticket")
    void shouldUpdateExistingTicket() throws Exception {
        SupportTicket savedTicket = createAndSaveTicket(300, "Old Category", "Old Assignee", Status.OPEN, Priority.LOW);

        SupportTicket updatedTicket = new SupportTicket();
        updatedTicket.setCustomerId(300);
        updatedTicket.setCatagory("Updated Category");
        updatedTicket.setAssignedTo("New Assignee");
        updatedTicket.setStatus(Status.RESOLVED);
        updatedTicket.setPriority(Priority.HIGH);

        mockMvc.perform(put("/supporttickets/" + savedTicket.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTicket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedTicket.getId().intValue())))
                .andExpect(jsonPath("$.catagory", is("Updated Category")))
                .andExpect(jsonPath("$.assignedTo", is("New Assignee")))
                .andExpect(jsonPath("$.status", is("RESOLVED")))
                .andExpect(jsonPath("$.priority", is("HIGH")));
    }

    @Test
    @DisplayName("PUT /supporttickets/{id} - Should create new ticket if ID not found")
    void shouldCreateNewTicketIfIdNotFound() throws Exception {
        SupportTicket newTicket = new SupportTicket();
        newTicket.setCustomerId(400);
        newTicket.setCatagory("New Ticket");
        newTicket.setAssignedTo("David");
        newTicket.setStatus(Status.OPEN);
        newTicket.setPriority(Priority.MEDIUM);

        mockMvc.perform(put("/supporttickets/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTicket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(400)))
                .andExpect(jsonPath("$.catagory", is("New Ticket")));
    }

    // ==================== DELETE Tests ====================

    @Test
    @DisplayName("DELETE /supporttickets/{id} - Should delete existing ticket")
    void shouldDeleteExistingTicket() throws Exception {
        SupportTicket savedTicket = createAndSaveTicket(500, "To Delete", "Eve", Status.CLOSED, Priority.LOW);

        mockMvc.perform(delete("/supporttickets/" + savedTicket.getId()))
                .andExpect(status().isOk());

        // Verify ticket is deleted - use customerId to check
        mockMvc.perform(get("/supporttickets/" + savedTicket.getCustomerId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /supporttickets/{id} - Should handle deletion of non-existent ticket")
    void shouldHandleDeletionOfNonExistentTicket() throws Exception {
        mockMvc.perform(delete("/supporttickets/99999"))
                .andExpect(status().isOk());
    }

    // ==================== Status and Priority Tests ====================

    @Test
    @DisplayName("Should support all status values")
    void shouldSupportAllStatusValues() throws Exception {
        for (Status status : Status.values()) {
            SupportTicket ticket = new SupportTicket();
            ticket.setCustomerId(600);
            ticket.setCatagory("Status Test");
            ticket.setAssignedTo("Tester");
            ticket.setStatus(status);
            ticket.setPriority(Priority.MEDIUM);

            mockMvc.perform(post("/supporttickets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(ticket)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", is(status.name())));
        }
    }

    @Test
    @DisplayName("Should support all priority values")
    void shouldSupportAllPriorityValues() throws Exception {
        for (Priority priority : Priority.values()) {
            SupportTicket ticket = new SupportTicket();
            ticket.setCustomerId(700);
            ticket.setCatagory("Priority Test");
            ticket.setAssignedTo("Tester");
            ticket.setStatus(Status.OPEN);
            ticket.setPriority(priority);

            mockMvc.perform(post("/supporttickets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(ticket)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priority", is(priority.name())));
        }
    }

    // ==================== Helper Methods ====================

    private SupportTicket createAndSaveTicket(int customerId, String category, String assignedTo,
                                               Status status, Priority priority) {
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomerId(customerId);
        ticket.setCatagory(category);
        ticket.setAssignedTo(assignedTo);
        ticket.setStatus(status);
        ticket.setPriority(priority);
        return repository.save(ticket);
    }
}
