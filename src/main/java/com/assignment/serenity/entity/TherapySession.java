package com.assignment.serenity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "therapy_session")  // Explicit table name
public class TherapySession {
    @Id
    @Column(name = "session_id")
    private String sessionId;

    private LocalDate date;
    private String time;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TherapyProgram program;
}