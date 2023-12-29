package br.com.tevitto.beleza_agendada.professional_schedule.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalScheduleRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ApiResponseDto;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalScheduleService;

@RestController
@RequestMapping("/v1")
public class ProfessionalScheduleController {

    @Autowired
    private ProfessionalScheduleService service;

    @Autowired
    private Validator validator;

    @PostMapping("/create")
    public ApiResponseDto create(@RequestBody CreateProfessionalScheduleRequest request) {
       ApiResponseDto response = null;

        Set<ConstraintViolation<CreateProfessionalScheduleRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    message);
        }

        try {
            response = new ApiResponseDto(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
                    service.create(request));
        } catch (Exception ex) {
            response = new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        } finally {
            return response;
        }
    }
}
