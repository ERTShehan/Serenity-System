package com.assignment.serenity.bo;

import com.assignment.serenity.bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {}

    public static synchronized BoFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public <T extends SuperBo> T getBo(BoType type) {
        switch (type) {
            case USER:
                return (T) new UserBoImpl();
            default:
                throw new IllegalArgumentException("No such BO type");
        }
    }

    public enum BoType {
        USER
    }
}