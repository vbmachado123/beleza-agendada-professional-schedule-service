package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateDayOfWeekRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.BreakTime;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.DayOfWeekItem;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalSchedule;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.DayOfWeekItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class DayOfWeekItemService {

    @Autowired
    private BreakTimeService breakTimeService;

    @Autowired
    private DayOfWeekItemRepository dayOfWeekItemRepository;

    public DayOfWeekItem createDayOfWeekItem(
            LocalDate day,
            CreateDayOfWeekRequest dayOfWeekRequest,
            ProfessionalSchedule professionalSchedule) {


        log.info("CREATE DAY OF WEEK: {} ", dayOfWeekRequest.getDay());

        List<BreakTime> breakTimes = breakTimeService.createBreakTimes(dayOfWeekRequest.getBreaktime());

        DayOfWeekItem dayOfWeekItem = DayOfWeekItem.builder()
                .date(day)
                .dayOfWeek(day.getDayOfWeek())
                .initTime(dayOfWeekRequest.getInitTime())
                .endTime(dayOfWeekRequest.getEndTime())
                .breakTime(breakTimes)
                .professional_schedule(professionalSchedule)
                .build();

        return dayOfWeekItemRepository.save(dayOfWeekItem);


    }


}
