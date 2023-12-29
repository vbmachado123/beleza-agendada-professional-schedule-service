package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BreakTime extends AbstractEntityBase {
    
    private Date initTime;
    
    private Date endTime;

    private String description;

    @ManyToOne
    private DayOfWeekItem dayOfWeek;

}
