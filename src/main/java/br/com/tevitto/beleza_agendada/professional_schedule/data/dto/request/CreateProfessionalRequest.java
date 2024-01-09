package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProfessionalRequest {

    private String professional_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder.Default
    private double interval = 15; // 15 min por padrao

    private List<CreateProfessionalDayRequest> daysOfWeek;

}
