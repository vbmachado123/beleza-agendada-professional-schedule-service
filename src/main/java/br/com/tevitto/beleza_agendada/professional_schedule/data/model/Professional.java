package br.com.tevitto.beleza_agendada.professional_schedule.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professional")
public class Professional extends AbstractEntityBase {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PROFESSIONAL_INTERVAL", nullable = false, columnDefinition = "double precision")
    private double interval;

}
