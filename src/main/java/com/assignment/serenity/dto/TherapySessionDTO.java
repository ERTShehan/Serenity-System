package com.assignment.serenity.dto;

import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.Therapist;
import com.assignment.serenity.entity.TherapyProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapySessionDTO {
    private String sessionId;
    private LocalDate date;
    private String time;
    private String status;
    private Patient patient;
    private Therapist therapist;
    private TherapyProgram program;
}