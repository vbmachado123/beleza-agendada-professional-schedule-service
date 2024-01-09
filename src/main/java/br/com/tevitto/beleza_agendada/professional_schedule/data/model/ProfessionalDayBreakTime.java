package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professional_day_breack_time")
public class ProfessionalDayBreakTime extends AbstractEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "INIT_TIME")
    private LocalTime initTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFESSIONAL_DAY_ID")
    @JsonBackReference
    private ProfessionalDay professionalDay;
}
