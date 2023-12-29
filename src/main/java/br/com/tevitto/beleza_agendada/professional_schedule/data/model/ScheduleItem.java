package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItem extends AbstractEntityBase {

    private Date dateTime;

    private UUID schedule_id;

    private Boolean available;

    @ManyToOne
    private ProfessionalSchedule professionalSchedule;

}
