package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class CreateProfessionalDayBreakTimeRequest {

    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime initTime;

    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime endTime;

    private String description;
}
