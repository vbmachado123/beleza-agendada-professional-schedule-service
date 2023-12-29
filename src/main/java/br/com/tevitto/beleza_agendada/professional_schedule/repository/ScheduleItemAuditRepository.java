package br.com.tevitto.beleza_agendada.professional_schedule.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ScheduleItemAudit;

public interface ScheduleItemAuditRepository extends JpaRepository<ScheduleItemAudit, UUID>, JpaSpecificationExecutor<ScheduleItemAudit> {
    
}
