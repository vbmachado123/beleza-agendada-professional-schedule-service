package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProfessionalScheduleRequest {

    private UUID professional_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder.Default
    private double interval = 15; // 15 min por padrao

    private List<CreateDayOfWeekRequest> daysOfWeek;

}
