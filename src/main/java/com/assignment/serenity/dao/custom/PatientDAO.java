package com.assignment.serenity.dao.custom;

import com.assignment.serenity.entity.Patient;

public interface PatientDAO extends CrudDAO<Patient> {
    Patient findById(String id);
}
