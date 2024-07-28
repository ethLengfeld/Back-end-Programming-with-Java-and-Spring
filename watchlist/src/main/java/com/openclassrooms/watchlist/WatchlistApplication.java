package com.openclassrooms.watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WatchlistApplication {
// Generate WAR file
//extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WatchlistApplication.class, args);
	}

}
