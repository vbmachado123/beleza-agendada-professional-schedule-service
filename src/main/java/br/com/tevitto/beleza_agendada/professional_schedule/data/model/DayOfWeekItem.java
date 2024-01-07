package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import br.com.tevitto.beleza_agendada.professional_schedule.data.enums.DayOfWeekType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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

    private LocalDate date;

    private LocalTime initTime;

    private LocalTime endTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BreakTime> breakTime;

    @Enumerated
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfessionalSchedule professional_schedule;

}
