package br.com.tevitto.beleza_agendada.professional_schedule.service;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateBreaktimeRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.BreakTime;
import br.com.tevitto.beleza_agendada.professional_schedule.repository.BreakTimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class BreakTimeService {

    @Autowired
    private BreakTimeRepository breakTimeRepository;

    public List<BreakTime> createBreakTimes(List<CreateBreaktimeRequest> breaktimeRequests) {
        List<BreakTime> breakTimes = new ArrayList<>();

        for (CreateBreaktimeRequest breaktimeRequest : breaktimeRequests) {
            BreakTime breakTime = BreakTime.builder()
                    .initTime(breaktimeRequest.getInitTime())
                    .endTime(breaktimeRequest.getInitTime())
                    .description(breaktimeRequest.getDescription())
                    .build();

            breakTime.setDeleted(Boolean.FALSE);
            breakTime.setStatus(201);
            breakTimes.add(breakTimeRepository.save(breakTime));
        }

        return breakTimes;
    }
}
