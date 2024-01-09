package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.Professional;
import br.com.tevitto.beleza_agendada.professional_schedule.execeptions.BusinessException;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.service.async.AsyncProfessionalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AsyncProfessionalService asyncService;

    @Autowired
    private Validator validator;


    @Transactional
    public ProfessionalScheduleCreatedResponse create(CreateProfessionalRequest request) {
        log.info("Creating Professinal Scheduler with request {}", request);

        validCreate(request);

        Professional professional = professionalRepository.findById(request.getProfessional_id()).orElseGet(() -> {
            Professional forSavSchedule = Professional.builder()
                    .id(request.getProfessional_id())
                    .interval(request.getInterval() <= 0 ? 15 : request.getInterval())
                    .build();

            return professionalRepository
                    .save(forSavSchedule);
        });


        asyncService.asyncCreateDaysProfessionalSchedule(request.getInitDate(), request.getEndDate(), request.getDaysOfWeek(),
                professional);

        return ProfessionalScheduleCreatedResponse.builder()
                .schedule_id(professional.getId())
                .initDate(request.getInitDate())
                .endDate(request.getEndDate())
                .build();
    }

    private void validCreate(CreateProfessionalRequest request) {
        if (request.getDaysOfWeek().size() < 7) {
            throw new RuntimeException("DaysOfWeek must be equals than week lenght");
        }
        Set<ConstraintViolation<CreateProfessionalRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new BusinessException(message);
        }
    }

}
