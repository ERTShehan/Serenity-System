package com.assignment.serenity.dao.custom;

import com.assignment.serenity.entity.Therapist;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Therapist getById(String therapistId);
}
