package br.com.tevitto.beleza_agendada.professional_schedule.service.async;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalDayRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.Professional;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalRepository;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayBreakTimeService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayHourService;
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
public class AsyncProfessionalService {

    @Autowired
    private ProfessionalDayHourService professionalDayHourService;

    @Autowired
    private ProfessionalDayService professionalDayService;

    @Autowired
    private ProfessionalDayBreakTimeService professionalDayBreakTimeService;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asyncCreateDaysProfessionalSchedule(LocalDate startCalendar, LocalDate endCalendar,
                                                    List<CreateProfessionalDayRequest> daysOfWeek,
                                                    Professional professional) {
        //TODO:colocar está chamada em uma fila para termos o retry de menssageria
        List<ProfessionalDay> professionalDays = startCalendar.datesUntil(endCalendar).map(day -> daysOfWeek.stream().filter(dayOfWeekRequest -> dayOfWeekRequest.getDay() == day.getDayOfWeek().getValue()).findFirst().map(dayOfWeekRequest -> professionalDayService.createProfessionalDay(day, dayOfWeekRequest, professional)).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
        professionalDays.forEach(dayOfWeekItem -> professionalDayHourService.createScheduleItems(dayOfWeekItem, professional));

    }


}
