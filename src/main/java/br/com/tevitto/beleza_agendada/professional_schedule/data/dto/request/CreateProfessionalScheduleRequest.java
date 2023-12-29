package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProfessionalScheduleRequest {
    
    private UUID professional_id;
    private Date initDate;
    private Date endDate;
    private double interval; // 15 min por padrao
    private List<CreateDayOfWeekRequest> daysOfWeek;
    
}
