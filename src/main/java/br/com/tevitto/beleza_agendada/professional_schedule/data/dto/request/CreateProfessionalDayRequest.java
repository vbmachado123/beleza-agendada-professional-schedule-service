package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProfessionalDayRequest {

    private int day;
    
    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime initTime;

    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime endTime;

    private List<CreateProfessionalDayBreakTimeRequest> breaktime;
    
}
