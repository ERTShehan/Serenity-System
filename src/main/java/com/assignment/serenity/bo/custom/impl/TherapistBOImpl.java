package com.assignment.serenity.bo.custom.impl;

import com.assignment.serenity.bo.custom.TherapistBO;
import com.assignment.serenity.dao.custom.TherapistDAO;
import com.assignment.serenity.dao.custom.impl.TherapistDAOImpl;
import com.assignment.serenity.dto.TherapistDTO;
import com.assignment.serenity.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapistBOImpl implements TherapistBO {
    private final TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.save(
                new Therapist(therapistDTO.getTherapistID(), therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(), therapistDTO.getAvailability(),
                        new ArrayList<>(), new ArrayList<>())
        );
    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.update(
                new Therapist(therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getAvailability(),
                        new ArrayList<>(), new ArrayList<>())
        );
    }

    @Override
    public boolean deleteTherapist(String therapistId) throws Exception {
        return therapistDAO.deleteByPK(therapistId);
    }

    @Override
    public ArrayList<TherapistDTO> loadAllTherapists() throws SQLException, ClassNotFoundException {
        ArrayList<TherapistDTO> therapistDTOS = new ArrayList<>();
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) therapistDAO.getAll();

        for (Therapist therapist : therapists) {
            therapistDTOS.add(
                    new TherapistDTO
                            (therapist.getTherapistID(), therapist.getTherapistName(), therapist.getSpecialization(), therapist.getAvailability()
                            ));
        }
        return therapistDTOS;
    }

    @Override
    public String getNaxtTherapistID() {
        try {
            return therapistDAO.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
            return "T00-001";
        }
    }
}
