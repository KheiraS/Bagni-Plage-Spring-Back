package fr.humanbooster.fx.plages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class PlagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlagesApplication.class, args);
	}

	// Le bean renvoyé par cette méthode va être accueilli dans le conteneur IoC de Spring
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
