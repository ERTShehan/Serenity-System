package com.assignment.serenity.dao.custom.impl;

import com.assignment.serenity.config.FactoryConfiguration;
import com.assignment.serenity.dao.custom.PaymentDAO;
import com.assignment.serenity.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public boolean save(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Payment paymentToDelete = session.get(Payment.class, id);
            if (paymentToDelete != null) {
                session.remove(paymentToDelete);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getAll() {
        try (Session session = factoryConfiguration.getSession()) {
            Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            String lastId = session.createQuery(
                            "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC", String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
                return String.format("PAY00-%03d", numericPart);
            } else {
                return "PAY00-001";
            }
        }
    }
}