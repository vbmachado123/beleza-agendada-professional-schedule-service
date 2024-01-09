package br.com.tevitto.beleza_agendada.professional_schedule.controller;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ProfessionalScheduleController {

    @Autowired
    private ProfessionalService service;

    @Autowired
    private ProfessionalDayService professionalDayService;

    @GetMapping("/agenda/{id}")
    public ResponseEntity<List<ProfessionalDay>> list(@PathVariable("id") String idProfessional, @RequestParam("yearMonth") YearMonth month) {
        return ResponseEntity.ok(professionalDayService.list(idProfessional, month));
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalScheduleCreatedResponse> create(@RequestBody @Valid CreateProfessionalRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
