package com.assignment.serenity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_program")

public class TherapyProgram {
    @Id
    @Column(name = "program_id", nullable = false, length = 50)
    private String programId;

    @Column(nullable = false, unique = true)
    private String programName;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    public <E> TherapyProgram(String programId, String programName, String duration, double fee, ArrayList<E> es, ArrayList<E> es1) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
    }

//    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL)
//    private List<TherapistProgram> therapistPrograms;
//
//    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL)
//    private List<TherapySession> therapySessions;
}
