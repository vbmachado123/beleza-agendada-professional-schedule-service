package br.com.tevitto.beleza_agendada.professional_schedule.repository;

import java.util.UUID;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProfessionalDayRepository extends JpaRepository<ProfessionalDay, UUID>, QuerydslPredicateExecutor<ProfessionalDay>, JpaSpecificationExecutor<ProfessionalDay> {
    
}
