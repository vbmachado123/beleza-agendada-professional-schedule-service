package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayHour;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfessionalScheduleCreatedResponse {

    private LocalDate initDate;
    private LocalDate endDate;
    private String schedule_id;
    private Date created_at;
    private List<ProfessionalDayHour> schedules_items;

}
