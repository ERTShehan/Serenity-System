package com.assignment.serenity.dao.custom;

import com.assignment.serenity.entity.TherapyProgram;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {
    TherapyProgram findById(String id);
}
