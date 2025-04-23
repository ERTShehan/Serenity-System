package com.assignment.serenity.bo.custom;

import com.assignment.serenity.dto.PaymentDTO;
import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.TherapyProgram;

import java.time.LocalDate;
import java.util.ArrayList;

public interface PaymentBO {
    boolean savePayment(PaymentDTO dto) throws Exception;
    boolean updatePayment(PaymentDTO dto) throws Exception;
    boolean deletePayment(String paymentId) throws Exception;
    ArrayList<PaymentDTO> getAllPayments() throws Exception;
    String getNextPaymentId() throws Exception;
}