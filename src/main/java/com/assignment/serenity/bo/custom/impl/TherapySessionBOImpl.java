package com.assignment.serenity.bo.custom.impl;

import com.assignment.serenity.bo.custom.TherapySessionBO;
import com.assignment.serenity.dao.custom.TherapySessionDAO;
import com.assignment.serenity.dao.custom.impl.TherapySessionDAOImpl;
import com.assignment.serenity.dto.TherapySessionDTO;
import com.assignment.serenity.entity.TherapySession;

import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    private final TherapySessionDAO therapySessionDAO = new TherapySessionDAOImpl();

    @Override
    public boolean saveSession(TherapySessionDTO dto) throws Exception {
        return therapySessionDAO.save(new TherapySession(
                dto.getSessionId(),
                dto.getDate(),
                dto.getTime(),
                dto.getStatus(),
                dto.getPatient(),
                dto.getTherapist(),
                dto.getProgram()
        ));
    }

    @Override
    public boolean updateSession(TherapySessionDTO dto) throws Exception {
        return therapySessionDAO.update(new TherapySession(
                dto.getSessionId(),
                dto.getDate(),
                dto.getTime(),
                dto.getStatus(),
                dto.getPatient(),
                dto.getTherapist(),
                dto.getProgram()
        ));
    }

    @Override
    public boolean deleteSession(String sessionId) throws Exception {
        return therapySessionDAO.deleteByPK(sessionId);
    }

    @Override
    public ArrayList<TherapySessionDTO> getAllSessions() throws Exception {
        List<TherapySession> sessions = therapySessionDAO.getAll();
        ArrayList<TherapySessionDTO> sessionDTOs = new ArrayList<>();

        if (sessions != null) {  // Additional null check for safety
            for (TherapySession session : sessions) {
                sessionDTOs.add(new TherapySessionDTO(
                        session.getSessionId(),
                        session.getDate(),
                        session.getTime(),
                        session.getStatus(),
                        session.getPatient(),
                        session.getTherapist(),
                        session.getProgram()
                ));
            }
        }
        return sessionDTOs;
    }

    @Override
    public String getNextSessionId() throws Exception {
        return therapySessionDAO.getNextId();
    }
}