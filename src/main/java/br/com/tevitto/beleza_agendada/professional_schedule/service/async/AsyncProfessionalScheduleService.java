package br.com.tevitto.beleza_agendada.professional_schedule.service.async;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateBreaktimeRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateDayOfWeekRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.enums.DayOfWeekType;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.BreakTime;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.DayOfWeekItem;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ScheduleItem;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AsyncProfessionalScheduleService {
    Logger logger = LogManager.getLogger(AsyncProfessionalScheduleService.class);

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

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asyncCreateDaysProfessionalSchedule(Calendar startCalendar, Calendar endDate,
                                                    List<CreateDayOfWeekRequest> days,
                                                    ProfessionalSchedule professionalSchedule, Date time,
                                                    TimeZone timeZone) {

        logger.info("RUN IN BACKGROUND, CREATE SCHEDULEITEMS: {}", professionalSchedule.getId());
        while (!startCalendar.after(endDate)) {

            for (CreateDayOfWeekRequest day : days) {
                createDayOfWeekItem(day, professionalSchedule, startCalendar.getTime(), timeZone);
            }

            startCalendar.add(Calendar.DATE, 1);
        }
    }

    private void createScheduleItems(DayOfWeekItem dayOfWeekItem, ProfessionalSchedule professionalSchedule) {
        double interval = professionalSchedule.getInterval();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayOfWeekItem.getInitTime());

        logger.info("CREATE SCHEDULE_ITEMS FOR: {}", dayOfWeekItem);

        while (calendar.getTime().before(dayOfWeekItem.getEndTime())) {
            calendar.add(Calendar.MINUTE, (int) interval);
            Date scheduleItemEndTime = calendar.getTime();

            ScheduleItem scheduleItem = ScheduleItem.builder()
                    .dateTime(scheduleItemEndTime)
                    .available(Boolean.TRUE)
                    .build();

            scheduleItem.setDeleted(Boolean.FALSE);
            scheduleItem.setStatus(201);

            ScheduleItem saved = scheduleItemRepository.save(scheduleItem);

            logger.info("SCHEDULE_ITEM CREATED: {} FOR DAY: {}", saved.getId(), dayOfWeekItem.getDayOfWeek());
        }
    }

    private void createDayOfWeekItem(
            CreateDayOfWeekRequest day,
            ProfessionalSchedule professionalSchedule,
            Date currentDate,
            TimeZone timeZone) {

        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(currentDate);

        Date initTime = parseTimeString(day.getInitTime(), calendar);
        Date endTime = parseTimeString(day.getEndTime(), calendar);

        logger.info("CREATE DAY OF WEEK: {} ", day.getDay());

        DayOfWeekItem dayOfWeekItem = DayOfWeekItem.builder()
                .initTime(initTime)
                .endTime(endTime)
                .dayOfWeek(convertDayOfWeekType(day.getDay()))
                .professional_schedule(professionalSchedule)
                .build();

        dayOfWeekItemRepository.save(dayOfWeekItem);


        createScheduleItems(dayOfWeekItem, professionalSchedule);

        if (day.getBreaktime() != null) {
            List<BreakTime> breakTimes = createBreakTimes(day.getBreaktime(), dayOfWeekItem, timeZone);
            dayOfWeekItem.setBreakTime(breakTimes);
        }
    }

    private List<BreakTime> createBreakTimes(List<CreateBreaktimeRequest> breaktimeRequests,
                                             DayOfWeekItem dayOfWeekItem, TimeZone timeZone) {
        List<BreakTime> breakTimes = new ArrayList<>();

        for (CreateBreaktimeRequest breaktimeRequest : breaktimeRequests) {
            BreakTime breakTime = BreakTime.builder()
                    .initTime(parseTimeString(breaktimeRequest.getInitTime(), timeZone))
                    .endTime(parseTimeString(breaktimeRequest.getEndTime(), timeZone))
                    .description(breaktimeRequest.getDescription())
                    .dayOfWeek(dayOfWeekItem)
                    .build();

            breakTime.setDeleted(Boolean.FALSE);
            breakTime.setStatus(201);
            BreakTime saved = breakTimeRepository.save(breakTime);
            breakTimes.add(saved);
            logger.info("CREATED BREAKTIME: {} - FOR DAY: {}", saved.getId(), dayOfWeekItem.getDayOfWeek());
        }

        return breakTimes;
    }

    private Date parseTimeString(String timeString, TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(timeZone);

        try {
            return sdf.parse(timeString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing time string: " + timeString, e);
        }
    }

    private Date parseTimeString(String timeString, Calendar calendar) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    private DayOfWeekType convertDayOfWeekType(int i) {
        switch (i) {
            case 1:
                return DayOfWeekType.MONDAY;
            case 2:
                return DayOfWeekType.SUNDAY;
            case 3:
                return DayOfWeekType.TUESDAY;
            case 4:
                return DayOfWeekType.WEDNESDAY;
            case 5:
                return DayOfWeekType.THURSDAY;
            case 6:
                return DayOfWeekType.FRIDAY;
            case 7:
                return DayOfWeekType.SATURDAY;
            default:
                throw new RuntimeException("DAY NOT FOUND: " + i);
        }
    }

}
