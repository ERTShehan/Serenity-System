package com.assignment.serenity.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapySessionTM {
    private String sessionId;
    private LocalDate date;
    private String time;
    private String status;
    private String patientName;
    private String therapistName;
    private String programName;
}