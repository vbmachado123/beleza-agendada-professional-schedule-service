package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto implements Serializable {
    private static final long serialVersionUID = -33444760721456493L;

    private Integer returnCode;
    private String returnMessage;
    private Object payload;


}