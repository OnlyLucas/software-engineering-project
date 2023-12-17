package com.flatfusion.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * The main class for the FlatFusion backend application.
 * This class contains the main method to start the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages ={"com.flatfusion.backend"})
public class BackendApplication {

	/**
	 * The main method to start the Spring Boot application.
	 *
	 * @param args The command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("Server started");
	}

}
