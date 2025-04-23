package com.assignment.serenity.bo.custom.impl;

import com.assignment.serenity.bo.custom.PaymentBO;
import com.assignment.serenity.dao.custom.PaymentDAO;
import com.assignment.serenity.dao.custom.impl.PaymentDAOImpl;
import com.assignment.serenity.dto.PaymentDTO;
import com.assignment.serenity.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean savePayment(PaymentDTO dto) throws Exception {
        return paymentDAO.save(new Payment(
                dto.getPaymentId(),
                dto.getDate(),
                dto.getAmount(),
                dto.getPaymentMethod(),
                dto.getPatient(),
                dto.getProgram()
        ));
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws Exception {
        return paymentDAO.update(new Payment(
                dto.getPaymentId(),
                dto.getDate(),
                dto.getAmount(),
                dto.getPaymentMethod(),
                dto.getPatient(),
                dto.getProgram()
        ));
    }

    @Override
    public boolean deletePayment(String paymentId) throws Exception {
        return paymentDAO.deleteByPK(paymentId);
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws Exception {
        List<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        if (payments != null) {
            for (Payment payment : payments) {
                paymentDTOs.add(new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getDate(),
                        payment.getAmount(),
                        payment.getPaymentMethod(),
                        payment.getPatient(),
                        payment.getProgram()
                ));
            }
        }
        return paymentDTOs;
    }

    @Override
    public String getNextPaymentId() throws Exception {
        return paymentDAO.getNextId();
    }
}