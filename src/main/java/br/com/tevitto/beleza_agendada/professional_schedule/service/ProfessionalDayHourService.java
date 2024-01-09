package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.model.Professional;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayHour;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.QProfessionalDayHour;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalDayHourRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class ProfessionalDayHourService {

    @Autowired
    private ProfessionalDayHourRepository professionalDayHourRepository;

    @Autowired
    private ProfessionalDayService professionalDayService;

    public ArrayList<ProfessionalDayHour> createScheduleItems(ProfessionalDay professionalDay, Professional professional) {

        int interval = (int) professional.getInterval();
        ArrayList<ProfessionalDayHour> professionalDayHours = new ArrayList<>();
        LocalTime scheduleTime = LocalTime.from(professionalDay.getInitTime());

        log.info("CREATE SCHEDULE_ITEMS FOR: {}", professionalDay);


        while (scheduleTime.isBefore(professionalDay.getEndTime())) {
            scheduleTime = scheduleTime.plusMinutes(interval);
            professionalDayHours.add(createScheduleItem(professional, scheduleTime, professionalDay.getDate()));
        }
        return professionalDayHours;
    }

    private ProfessionalDayHour createScheduleItem(Professional professional, LocalTime scheduleTime, LocalDate date) {

        ProfessionalDayHour professionalDayHour = ProfessionalDayHour.builder()
                .date(date)
                .dateTime(scheduleTime)
                .available(Boolean.TRUE)
                .professional_id(professional.getId())
                .build();

        return professionalDayHourRepository.save(professionalDayHour);
    }

    public List<ProfessionalDayHour> getDayHours(String idProfessional, UUID idDay) {
        ProfessionalDay professionalDay = professionalDayService.getById(idDay);

        BooleanExpression expression = QProfessionalDayHour.professionalDayHour.professional_id.eq(idProfessional);
        expression = expression.and(QProfessionalDayHour.professionalDayHour.date.eq(professionalDay.getDate()));
        OrderSpecifier<LocalDate> orderSpecifier = new OrderSpecifier(Order.ASC, QProfessionalDayHour.professionalDayHour.dateTime);

        return StreamSupport.stream(professionalDayHourRepository.findAll(expression, orderSpecifier).spliterator(), false).collect(Collectors.toList());
    }
}
