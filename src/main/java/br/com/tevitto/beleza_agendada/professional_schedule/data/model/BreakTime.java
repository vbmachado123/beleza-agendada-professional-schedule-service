package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BreakTime extends AbstractEntityBase {

    private LocalTime initTime;

    private LocalTime endTime;

    private String description;

}
