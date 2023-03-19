package com.example.bulletinboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BulletinboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulletinboardApplication.class, args);
	}

}
