package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ScheduleItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfessionalScheduleCreatedResponse {

    private LocalDate initDate;
    private LocalDate endDate;
    private UUID schedule_id;
    private Date created_at;
    private List<ScheduleItem> schedules_items;

}
