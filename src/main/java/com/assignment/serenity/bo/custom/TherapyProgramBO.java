package com.assignment.serenity.bo.custom;

import com.assignment.serenity.dto.TherapyProgramDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramBO {
    boolean saveTherapyProgram(TherapyProgramDTO therapyProgramDTO);
    boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO);
    boolean deleteTherapyProgram(String id) throws Exception;
    ArrayList<TherapyProgramDTO> loadAllTherapyPrograms() throws SQLException, ClassNotFoundException;
    String getNextTherapyProgramId();
}
