package com.crtneko.springsecurity;

import static com.crtneko.springsecurity.models.Roles.ADMIN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
		
		System.out.println(ADMIN.getAuthorities());
		System.out.println();

	}

}
