package br.com.tevitto.beleza_agendada.professional_schedule.handler;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ZuulHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAnyException(
            Exception ex, WebRequest request) {

        ApiResponseDto errorResponse = ApiResponseDto.builder()
                .returnCode(HttpStatus.BAD_REQUEST.value())
                .returnMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .payload(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}