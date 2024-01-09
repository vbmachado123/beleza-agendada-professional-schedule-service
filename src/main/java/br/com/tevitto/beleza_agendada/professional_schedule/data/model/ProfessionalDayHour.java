package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professional_day_hour")
public class ProfessionalDayHour extends AbstractEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;

    @Column(name = "PROFESSIONAL_DAY_HOUR_DATE")
    private LocalDate date;

    @Column(name = "DATE_TIME")
    private LocalTime dateTime;

    @Column(name = "AVAILABLE")
    private Boolean available;

    @Column(name = "PROFESSIONAL_ID")
    private String professional_id;

}
