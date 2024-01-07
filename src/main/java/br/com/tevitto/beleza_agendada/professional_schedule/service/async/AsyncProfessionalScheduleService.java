package br.com.tevitto.beleza_agendada.professional_schedule.service.async;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateDayOfWeekRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.DayOfWeekItem;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.service.BreakTimeService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.DayOfWeekItemService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ScheduleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AsyncProfessionalScheduleService {

    @Autowired
    private ScheduleItemService scheduleItemService;

    @Autowired
    private DayOfWeekItemService dayOfWeekItemService;

    @Autowired
    private BreakTimeService breakTimeService;


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asyncCreateDaysProfessionalSchedule(LocalDate startCalendar, LocalDate endCalendar,
                                                    List<CreateDayOfWeekRequest> daysOfWeek,
                                                    ProfessionalSchedule professionalSchedule) {
        //TODO:colocar está chamada em uma fila para termos o retry de menssageria
        List<DayOfWeekItem> dayOfWeekItems = startCalendar.datesUntil(endCalendar).map(day -> daysOfWeek.stream().filter(dayOfWeekRequest -> dayOfWeekRequest.getDay() == day.getDayOfWeek().getValue()).findFirst().map(dayOfWeekRequest -> dayOfWeekItemService.createDayOfWeekItem(day, dayOfWeekRequest, professionalSchedule)).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
        dayOfWeekItems.forEach(dayOfWeekItem -> scheduleItemService.createScheduleItems(dayOfWeekItem, professionalSchedule));

    }


}
