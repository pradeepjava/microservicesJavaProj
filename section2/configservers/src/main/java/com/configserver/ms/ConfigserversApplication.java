package com.configserver.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigserversApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserversApplication.class, args);
	}

}
