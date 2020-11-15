package com.johnbryce.cs2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cs2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cs2Application.class, args);
		System.out.println("IoC container was loaded");
	}

}
