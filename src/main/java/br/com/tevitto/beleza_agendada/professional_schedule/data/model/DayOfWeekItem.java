package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import br.com.tevitto.beleza_agendada.professional_schedule.data.enums.DayOfWeekType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DayOfWeekItem {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    private Date initTime;
    
    private Date endTime;
    
    @OneToMany
    private List<BreakTime> breakTime;

    @Enumerated
    private DayOfWeekType dayOfWeek;

    @ManyToOne
    private ProfessionalSchedule professional_schedule;

}
