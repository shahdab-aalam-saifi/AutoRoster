package com.saalamsaifi.auto.roster;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoRosterApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(AutoRosterApplication.class, args);
	}

}
