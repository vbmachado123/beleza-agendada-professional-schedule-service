package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.DayOfWeekItem;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ScheduleItem;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ScheduleItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
@Log4j2
public class ScheduleItemService {

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    public ArrayList<ScheduleItem> createScheduleItems(DayOfWeekItem dayOfWeekItem, ProfessionalSchedule professionalSchedule) {

        int interval = (int) professionalSchedule.getInterval();
        ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
        LocalTime scheduleTime = LocalTime.from(dayOfWeekItem.getInitTime());

        log.info("CREATE SCHEDULE_ITEMS FOR: {}", dayOfWeekItem);


        while (scheduleTime.isBefore(dayOfWeekItem.getEndTime())) {
            scheduleTime = scheduleTime.plusMinutes(interval);
            scheduleItems.add(createScheduleItem(professionalSchedule, scheduleTime, dayOfWeekItem.getDate()));
        }
        return scheduleItems;
    }

    private ScheduleItem createScheduleItem(ProfessionalSchedule professionalSchedule, LocalTime scheduleTime, LocalDate date) {

        ScheduleItem scheduleItem = ScheduleItem.builder()
                .date(date)
                .dateTime(scheduleTime)
                .available(Boolean.TRUE)
                .professionalSchedule(professionalSchedule)
                .build();

        scheduleItem.setDeleted(Boolean.FALSE);
        scheduleItem.setStatus(201);


        return scheduleItemRepository.save(scheduleItem);
    }
}
