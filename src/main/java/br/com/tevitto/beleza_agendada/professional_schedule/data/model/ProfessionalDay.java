package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professional_day")
public class ProfessionalDay extends AbstractEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;

    @Column(name = "PROFESSIONAL_DATE")
    private LocalDate date;

    @Column(name = "INIT_TIME")
    private LocalTime initTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @OneToMany(mappedBy = "professionalDay", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Builder.Default
    private List<ProfessionalDayBreakTime> breakTime = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;

    @Column(name = "PROFESSIONAL_ID")
    private String professional_id;

}
