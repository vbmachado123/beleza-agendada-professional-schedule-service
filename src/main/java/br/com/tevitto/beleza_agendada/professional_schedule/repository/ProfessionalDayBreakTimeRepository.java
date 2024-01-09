package br.com.tevitto.beleza_agendada.professional_schedule.repository;

import java.util.UUID;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayBreakTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfessionalDayBreakTimeRepository extends JpaRepository<ProfessionalDayBreakTime, UUID>, JpaSpecificationExecutor<ProfessionalDayBreakTime> {
    
}
