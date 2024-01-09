package br.com.tevitto.beleza_agendada.professional_schedule.repository;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.Professional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessionalRepository extends CrudRepository<Professional, String>, JpaSpecificationExecutor<Professional> {

}
