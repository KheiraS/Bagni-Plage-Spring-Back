package fr.humanbooster.fx.plages.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.humanbooster.fx.plages.handler.CustomFailureHandler;
import fr.humanbooster.fx.plages.handler.CustomSuccessHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {
	//Encryptage du mot de passe et lecture
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf (csrf-> csrf.disable())		
			// gestion de la connexion
			.formLogin(login -> login.loginPage("/index")
										 .loginProcessingUrl("/login")
			    // gestion des handlers
			    .successHandler(new CustomSuccessHandler())
				.failureHandler(new CustomFailureHandler()))
			// gestion de la déconnexion
			.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/index"))
			// gestion des accès
			.authorizeHttpRequests(requests -> 
				requests.antMatchers("/swagger-ui/index.html").permitAll()
						.antMatchers("/api/login").permitAll()
						.antMatchers("/index").permitAll()
						.antMatchers("/api-autogeneree/files").permitAll()
						.antMatchers("/api-autogeneree/payses").permitAll()
						.antMatchers("/parasols").authenticated()
						.antMatchers(HttpMethod.GET, "/clients").hasRole("ADMIN")
						.antMatchers(HttpMethod.POST, "/reservation").hasRole("ADMIN")
						.antMatchers(HttpMethod.GET, "/reservations").hasAnyRole("USER", "ADMIN"))
				.headers(header -> header.frameOptions().disable());
		
		return http.build();
	}

	/*
	@Bean
	InMemoryUserDetailsManager initUtilisateurs() {
		UserDetails toto = User.builder().username("toto").password(passwordEncoder.encode("toto")).roles("ADMIN")
				.build();
		UserDetails titi = User.builder().username("titi").password(passwordEncoder.encode("titi")).roles("USER").build();
		return new InMemoryUserDetailsManager(toto, titi);
	}
	*/
}