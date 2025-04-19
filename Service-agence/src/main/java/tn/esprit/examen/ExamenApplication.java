package tn.esprit.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient    //EurekaClient
@EnableFeignClients
public class ExamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenApplication.class, args);
	}

}
