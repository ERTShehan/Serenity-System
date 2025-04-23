package com.assignment.serenity.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTM {
    private String paymentId;
    private LocalDate date;
    private double amount;
    private String paymentMethod;
    private String patientName;
    private String programName;
}