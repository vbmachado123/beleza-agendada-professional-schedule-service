package br.com.tevitto.beleza_agendada.professional_schedule.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateBreaktimeRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateDayOfWeekRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalScheduleRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.enums.DayOfWeekType;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.BreakTime;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.DayOfWeekItem;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ScheduleItem;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.BreakTimeRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.DayOfWeekAuditRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.DayOfWeekItemRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalScheduleRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ScheduleItemAuditRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ScheduleItemRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.service.async.AsyncProfessionalScheduleService;

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

    @Transactional
    public ProfessionalScheduleCreatedResponse create(CreateProfessionalScheduleRequest request) {

        logger.info("==== CREATE PROFESSIONAL SCHEDULE STARTED ====");
        try {

            if (request.getDaysOfWeek().size() < 7)
                throw new RuntimeException("DaysOfWeek must be equals than week lenght");

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

            asyncService.asyncCreateProfessionalSchedule(startCalendar, endCalendar, request.getDaysOfWeek(),
                    professionalSchedule, endDate, timeZone);

            return ProfessionalScheduleCreatedResponse.builder()
                    .created_at(professionalSchedule.getCreateDate())
                    .schedule_id(professionalSchedule.getId())
                    .initDate(startCalendar.getTime())
                    .endDate(endCalendar.getTime())
                    .build();
        } catch (Exception ex) {
            logger.debug("==== ERROR IN CREATE PROFESSIONAL SCHEDULE: {}", ex.getMessage());
        }

        logger.info("==== CREATE PROFESSIONAL SCHEDULE FINISHED ====");

        return null;
    }

}
