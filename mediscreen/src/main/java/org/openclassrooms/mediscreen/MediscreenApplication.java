package org.openclassrooms.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MediscreenApplication {

	@RequestMapping("/")
	public String home() {
		return "Welcome to Mediscreen";
	}

	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}

}
