package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProfessionalSchedule extends AbstractEntityBase{
   
    private UUID professional_id;

    @Column(name = "interval_item", nullable = false, columnDefinition = "double precision")
    private double interval;

    @OneToMany
    private List<DayOfWeekItem> days;

    @OneToMany
    private List<ScheduleItem> schedules;
}
