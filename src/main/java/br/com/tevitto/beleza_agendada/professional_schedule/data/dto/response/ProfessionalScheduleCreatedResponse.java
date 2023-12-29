package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfessionalScheduleCreatedResponse {

    private Date initDate;
    private Date endDate;
    private UUID schedule_id;
    private Date created_at;
    private List<ScheduleItemCreatedResponse> schedules_items;

}
