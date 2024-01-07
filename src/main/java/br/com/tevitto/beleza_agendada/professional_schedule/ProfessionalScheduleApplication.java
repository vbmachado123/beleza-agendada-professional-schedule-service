package br.com.tevitto.beleza_agendada.professional_schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProfessionalScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfessionalScheduleApplication.class, args);
    }

}
