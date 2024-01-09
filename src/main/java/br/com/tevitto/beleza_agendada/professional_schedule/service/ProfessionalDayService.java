package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalDayRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.Professional;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.QProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalDayRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class ProfessionalDayService {

    @Autowired
    private ProfessionalDayBreakTimeService professionalDayBreakTimeService;

    @Autowired
    private ProfessionalDayRepository professionalDayRepository;


    public ProfessionalDay getById(UUID id) {

        return professionalDayRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Dia não encontrado");
        });
    }

    public List<ProfessionalDay> list(String idProfessional, YearMonth month) {
        OrderSpecifier<LocalDate> orderSpecifier = new OrderSpecifier(Order.ASC, QProfessionalDay.professionalDay.date);

        BooleanExpression expression = QProfessionalDay.professionalDay.date.month().eq(month.getMonthValue())
                .and(QProfessionalDay.professionalDay.date.year().eq(month.getYear())
                        .and(QProfessionalDay.professionalDay.professional_id.eq(idProfessional)));

        return StreamSupport.stream(professionalDayRepository.findAll(expression, orderSpecifier)
                .spliterator(), false).collect(Collectors.toList());
    }

    public ProfessionalDay createProfessionalDay(
            LocalDate day,
            CreateProfessionalDayRequest dayOfWeekRequest,
            Professional professional) {


        log.info("CREATE DAY OF WEEK: {} ", dayOfWeekRequest.getDay());


        ProfessionalDay professionalDay = professionalDayRepository.save(ProfessionalDay.builder()
                .date(day)
                .dayOfWeek(day.getDayOfWeek())
                .initTime(dayOfWeekRequest.getInitTime())
                .endTime(dayOfWeekRequest.getEndTime())
                .professional_id(professional.getId())
                .build());

        professionalDayBreakTimeService.create(dayOfWeekRequest.getBreaktime(), professionalDay);

        return professionalDay;


    }


}
