package br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto implements Serializable {
    private static final long serialVersionUID = -33444760721456493L;

    private Integer returnCode;
    private String returnMessage;
    private Object payload;

    public ApiResponseDto() {
        //default
    }

    /**
     * @param returnCode
     * @param returnMessage
     * @param payload
     */
    public ApiResponseDto(final Integer returnCode, final String returnMessage, final Object payload) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.payload = payload;
    }

}