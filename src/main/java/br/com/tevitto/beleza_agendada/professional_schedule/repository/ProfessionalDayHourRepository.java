package br.com.tevitto.beleza_agendada.professional_schedule.repository;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ProfessionalDayHourRepository extends JpaRepository<ProfessionalDayHour, UUID>, QuerydslPredicateExecutor<ProfessionalDayHour>, JpaSpecificationExecutor<ProfessionalDayHour> {

}
