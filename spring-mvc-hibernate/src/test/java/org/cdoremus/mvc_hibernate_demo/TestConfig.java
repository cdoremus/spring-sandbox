package org.cdoremus.mvc_hibernate_demo;

import org.cdoremus.mvc_hibernate_demo.config.HibernateDataConfig;
import org.cdoremus.mvc_hibernate_demo.data.ChirpRepository;
import org.cdoremus.mvc_hibernate_demo.data.ChirpUserRepository;
import org.cdoremus.mvc_hibernate_demo.data.HibernateChirpRepository;
import org.cdoremus.mvc_hibernate_demo.data.HibernateChirpUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HibernateDataConfig.class)
public class TestConfig {

	
	@Bean
	public ChirpUserRepository chirpUserRepository() {
		return new HibernateChirpUserRepository();
	}
	
	@Bean
	public ChirpRepository chirpRepository() {
		return new HibernateChirpRepository();
	}
}
