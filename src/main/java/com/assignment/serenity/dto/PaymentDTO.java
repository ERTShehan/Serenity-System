package com.assignment.serenity.dto;

import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.TherapyProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String paymentId;
    private LocalDate date;
    private double amount;
    private String paymentMethod;
    private Patient patient;
    private TherapyProgram program;
}