package br.com.tevitto.beleza_agendada.professional_schedule.controller;

import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.request.CreateProfessionalRequest;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ApiResponseDto;
import br.com.tevitto.beleza_agendada.professional_schedule.data.dto.response.ProfessionalScheduleCreatedResponse;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDay;
import br.com.tevitto.beleza_agendada.professional_schedule.data.model.ProfessionalDayHour;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayHourService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalDayService;
import br.com.tevitto.beleza_agendada.professional_schedule.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class ProfessionalScheduleController {

    @Autowired
    private ProfessionalService service;

    @Autowired
    private ProfessionalDayService professionalDayService;

    @Autowired
    private ProfessionalDayHourService professionalDayHourService;

    @GetMapping("/agenda/{id}/{idDay}")
    public ApiResponseDto list(@PathVariable("id") String idProfessional, @PathVariable("idDay") UUID idDay) {
        List<ProfessionalDayHour> response = professionalDayHourService.getDayHours(idProfessional, idDay);
        return ApiResponseDto.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage(HttpStatus.OK.getReasonPhrase())
                .payload(response)
                .build();
    }

    @GetMapping("/agenda/{id}")
    public ApiResponseDto list(@PathVariable("id") String idProfessional, @RequestParam("yearMonth") YearMonth month) {
        List<ProfessionalDay> response = professionalDayService.list(idProfessional, month);
        return ApiResponseDto.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage(HttpStatus.OK.getReasonPhrase())
                .payload(response)
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalScheduleCreatedResponse> create(@RequestBody @Valid CreateProfessionalRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
