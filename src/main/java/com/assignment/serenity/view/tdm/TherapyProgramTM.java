package com.assignment.serenity.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapyProgramTM {
    private String programId;
    private String programName;
    private String duration;
    private double fee;
}
