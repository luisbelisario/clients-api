package com.reservei.clientsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsApiApplication.class, args);
	}

}
