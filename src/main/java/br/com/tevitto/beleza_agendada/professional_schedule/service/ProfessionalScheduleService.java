package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalScheduleRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.execeptions.BusinessException;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.*;
import br.com.tevitto.beleza_agendada.professional_schedule.service.async.AsyncProfessionalScheduleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class ProfessionalScheduleService {

    Logger logger = LogManager.getLogger(ProfessionalScheduleService.class);

    @Autowired
    private BreakTimeRepository breakTimeRepository;

    @Autowired
    private DayOfWeekAuditRepository dayOfWeekAuditRepository;

    @Autowired
    private DayOfWeekItemRepository dayOfWeekItemRepository;

    @Autowired
    private ProfessionalScheduleRepository professionalScheduleRepository;

    @Autowired
    private ScheduleItemAuditRepository scheduleItemAuditRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Autowired
    private AsyncProfessionalScheduleService asyncService;

    @Autowired
    private Validator validator;


    @Transactional
    public ProfessionalScheduleCreatedResponse create(CreateProfessionalScheduleRequest request) {
        logger.info("Creating Professinal Scheduler with request {}", request);

        validCreate(request);

        TimeZone timeZone = TimeZone.getDefault();

        ProfessionalSchedule forSavSchedule = ProfessionalSchedule.builder()
                .professional_id(request.getProfessional_id())
                .interval(request.getInterval() <= 0 ? 15 : request.getInterval())
                .build();

        forSavSchedule.setDeleted(Boolean.FALSE);
        forSavSchedule.setStatus(201);

        ProfessionalSchedule professionalSchedule = professionalScheduleRepository
                .save(forSavSchedule);

        Date endDate = request.getEndDate();
        Calendar startCalendar = Calendar.getInstance(timeZone);
        startCalendar.setTime(request.getInitDate());

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(request.getEndDate());

        asyncService.asyncCreateDaysProfessionalSchedule(startCalendar, endCalendar, request.getDaysOfWeek(),
                professionalSchedule, endDate, timeZone);

        return ProfessionalScheduleCreatedResponse.builder()
                .created_at(professionalSchedule.getCreateDate())
                .schedule_id(professionalSchedule.getId())
                .initDate(startCalendar.getTime())
                .endDate(endCalendar.getTime())
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
