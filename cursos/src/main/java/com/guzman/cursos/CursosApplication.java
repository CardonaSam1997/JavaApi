package com.guzman.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //habilita la libreria Open feig
@SpringBootApplication
public class CursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursosApplication.class, args);
	}

}
