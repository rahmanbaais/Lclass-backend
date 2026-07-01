package com.lclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryLclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryLclassApplication.class, args);
	}

}
