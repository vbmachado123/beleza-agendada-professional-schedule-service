package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalDayBreakTimeRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayBreakTime;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.ProfessionalDayBreakTimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProfessionalDayBreakTimeService {

    @Autowired
    private ProfessionalDayBreakTimeRepository professionalDayBreakTimeRepository;

    public List<ProfessionalDayBreakTime> create(List<CreateProfessionalDayBreakTimeRequest> breaktimeRequests, ProfessionalDay professionalDay) {
        List<ProfessionalDayBreakTime> professionalDayBreakTimes = new ArrayList<>();

        for (CreateProfessionalDayBreakTimeRequest breaktimeRequest : breaktimeRequests) {
            ProfessionalDayBreakTime professionalDayBreakTime = ProfessionalDayBreakTime.builder()
                    .initTime(breaktimeRequest.getInitTime())
                    .endTime(breaktimeRequest.getInitTime())
                    .professionalDay(professionalDay)
                    .description(breaktimeRequest.getDescription())
                    .build();


            professionalDayBreakTimes.add(professionalDayBreakTimeRepository.save(professionalDayBreakTime));
        }

        return professionalDayBreakTimes;
    }
}
