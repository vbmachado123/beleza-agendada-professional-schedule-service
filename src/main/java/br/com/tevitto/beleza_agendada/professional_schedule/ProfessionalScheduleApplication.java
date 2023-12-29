package br.com.tevitto.beleza_agendada.professional_schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProfessionalScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfessionalScheduleApplication.class, args);
	}

}
