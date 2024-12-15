package com.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	 @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.disable())
	            .authorizeHttpRequests(auth -> 
	                auth.requestMatchers("/api/v1/ticket/**").permitAll()
	                    .anyRequest().authenticated()
	            )
	            .httpBasic(httpBasic -> {});
	        return http.build();
	    }
}