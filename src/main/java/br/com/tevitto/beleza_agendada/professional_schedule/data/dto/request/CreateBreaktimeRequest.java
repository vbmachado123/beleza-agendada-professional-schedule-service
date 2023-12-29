package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateBreaktimeRequest {
    
    private String initTime;
    private String endTime;
    private String description;
}
