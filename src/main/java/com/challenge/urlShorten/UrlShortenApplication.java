package com.challenge.urlShorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class UrlShortenApplication {

	public static void main(String[] args) {
		String environment = System.getenv("ENVIRONMENT"); 
        
        if ("development".equalsIgnoreCase(environment)) {
            Dotenv dotenv = Dotenv.configure()
                                  .directory("./")
                                  .load();
            System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
            System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
        }
		SpringApplication.run(UrlShortenApplication.class, args);
	}

}
