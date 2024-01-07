package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalScheduleRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.execeptions.BusinessException;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalScheduleRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.service.async.AsyncProfessionalScheduleService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProfessionalScheduleService {

    @Autowired
    private ProfessionalScheduleRepository professionalScheduleRepository;

    @Autowired
    private AsyncProfessionalScheduleService asyncService;

    @Autowired
    private Validator validator;


    @Transactional
    public ProfessionalScheduleCreatedResponse create(CreateProfessionalScheduleRequest request) {
        log.info("Creating Professinal Scheduler with request {}", request);

        validCreate(request);

        ProfessionalSchedule forSavSchedule = ProfessionalSchedule.builder()
                .professional_id(request.getProfessional_id())
                .interval(request.getInterval() <= 0 ? 15 : request.getInterval())
                .build();

        forSavSchedule.setDeleted(Boolean.FALSE);
        forSavSchedule.setStatus(201);

        ProfessionalSchedule professionalSchedule = professionalScheduleRepository
                .save(forSavSchedule);

        asyncService.asyncCreateDaysProfessionalSchedule(request.getInitDate(), request.getEndDate(), request.getDaysOfWeek(),
                professionalSchedule);

        return ProfessionalScheduleCreatedResponse.builder()
                .created_at(professionalSchedule.getCreateDate())
                .schedule_id(professionalSchedule.getId())
                .initDate(request.getInitDate())
                .endDate(request.getEndDate())
                .build();


    }

    private void validCreate(CreateProfessionalScheduleRequest request) {
        if (request.getDaysOfWeek().size() < 7) {
            throw new RuntimeException("DaysOfWeek must be equals than week lenght");
        }
        Set<ConstraintViolation<CreateProfessionalScheduleRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new BusinessException(message);
        }
    }

}
