package com.assignment.serenity.dao;

import com.assignment.serenity.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {}

    public static synchronized DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public <T extends SuperDao> T getDao(DaoType type) {
        switch (type) {
            case USER:
                return (T) new UserDaoImpl();
            default:
                throw new IllegalArgumentException("No such DAO type");
        }
    }

    public enum DaoType {
        USER
    }
}