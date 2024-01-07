package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItem extends AbstractEntityBase {

    private LocalDate date;

    private LocalTime dateTime;

    private UUID schedule_id;

    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfessionalSchedule professionalSchedule;

}
