package br.com.tevitto.beleza_agendada.professional_schedule.controller;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalScheduleRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class ProfessionalScheduleController {

    @Autowired
    private ProfessionalScheduleService service;


    @PostMapping("/create")
    public ResponseEntity<ProfessionalScheduleCreatedResponse> create(@RequestBody @Valid CreateProfessionalScheduleRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
