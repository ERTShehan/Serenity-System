package com.assignment.serenity.bo.custom;

import com.assignment.serenity.dto.TherapySessionDTO;
import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.Therapist;
import com.assignment.serenity.entity.TherapyProgram;

import java.time.LocalDate;
import java.util.ArrayList;

public interface TherapySessionBO {
    boolean saveSession(TherapySessionDTO dto) throws Exception;
    boolean updateSession(TherapySessionDTO dto) throws Exception;
    boolean deleteSession(String sessionId) throws Exception;
    ArrayList<TherapySessionDTO> getAllSessions() throws Exception;
    String getNextSessionId() throws Exception;
}