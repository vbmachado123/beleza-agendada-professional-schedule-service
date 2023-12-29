package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDayOfWeekRequest {

    private int day;
    private String initTime;
    private String endTime;
    private List<CreateBreaktimeRequest> breaktime;
    
}
