package com.assignment.serenity.dao.custom.impl;

import com.assignment.serenity.config.FactoryConfiguration;
import com.assignment.serenity.dao.custom.UserDao;
import com.assignment.serenity.entity.User;
import com.assignment.serenity.exception.UserNameDuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws UserNameDuplicateException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            User existingUser = search(user.getUsername());
            if (existingUser != null) {
                throw new UserNameDuplicateException("Username already exists");
            }

            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public User search(String username) throws IOException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    @Override
    public User authenticate(String username, String password) throws IOException {
        User user = search(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


}