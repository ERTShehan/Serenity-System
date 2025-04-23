package com.assignment.serenity.dao.custom.impl;

import jakarta.persistence.Query;
import com.assignment.serenity.bo.exception.DuplicateException;
import com.assignment.serenity.config.FactoryConfiguration;
import com.assignment.serenity.dao.custom.TherapistDAO;
import com.assignment.serenity.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public Therapist getById(String therapistId) {
        try {
            Session session = factoryConfiguration.getSession();
            Therapist therapist = session.get(Therapist.class, therapistId);
            return therapist;
        } catch (Exception e) {
            throw new RuntimeException("Therapist not found", e);
        }
    }

    @Override
    public boolean save(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Check therapist
            Therapist existingTherapist = session.get(Therapist.class, entity.getTherapistID());
            if (existingTherapist != null) {
                throw new DuplicateException("Therapist ID already exists");
            }

            session.persist(entity); // Save
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity); // Update the therapist
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, id);
            if (therapist != null) {
                session.delete(therapist); // Delete the therapist
                transaction.commit();
                return true;
            } else {
                throw new Exception("Therapist not found");
            }
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Therapist> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("FROM Therapist", Therapist.class);
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) ((org.hibernate.query.Query<?>) query).list();
        return therapists;
    }

    @Override
    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            String lastId = session.createQuery(
                            "SELECT t.therapistID FROM Therapist t ORDER BY t.therapistID DESC",
                            String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                String numericPart = lastId.substring(3); // Assuming format "T00-001"
                int nextNum = Integer.parseInt(numericPart) + 1;
                return String.format("T00-%03d", nextNum);
            } else {
                return "T00-001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "T00-001";
        }
    }
}
