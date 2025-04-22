package com.assignment.serenity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapistDTO {
    private String therapistID;
    private String therapistName;
    private String specialization;
    private String availability;
}
