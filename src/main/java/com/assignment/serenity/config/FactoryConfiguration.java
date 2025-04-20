package com.assignment.serenity.config;

import com.assignment.serenity.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() throws IOException {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/hibernate.properties"));

        configuration.setProperties(properties)
                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Patient.class)
//                .addAnnotatedClass(Program.class)
//                .addAnnotatedClass(Therapist.class)
//                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Session.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() throws IOException {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}