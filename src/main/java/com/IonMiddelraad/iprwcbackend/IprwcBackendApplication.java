package com.IonMiddelraad.iprwcbackend;

import com.IonMiddelraad.iprwcbackend.controller.EmployeeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class IprwcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprwcBackendApplication.class, args);
	}

}
