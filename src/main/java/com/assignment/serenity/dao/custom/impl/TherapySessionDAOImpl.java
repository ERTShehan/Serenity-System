package com.assignment.serenity.dao.custom.impl;

import com.assignment.serenity.config.FactoryConfiguration;
import com.assignment.serenity.dao.custom.TherapySessionDAO;
import com.assignment.serenity.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public boolean save(TherapySession entity) {
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
    public boolean update(TherapySession entity) {
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
            TherapySession sessionToDelete = session.get(TherapySession.class, id);
            if (sessionToDelete != null) {
                session.remove(sessionToDelete);
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
    public List<TherapySession> getAll() {
        try (Session session = factoryConfiguration.getSession()) {
            Query<TherapySession> query = session.createQuery("FROM TherapySession", TherapySession.class);
            List<TherapySession> sessions = query.getResultList();
            return sessions != null ? sessions : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            String lastId = session.createQuery(
                            "SELECT ts.sessionId FROM TherapySession ts ORDER BY ts.sessionId DESC", String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
                return String.format("TS00-%03d", numericPart);
            } else {
                return "TS00-001";
            }
        }
    }
}