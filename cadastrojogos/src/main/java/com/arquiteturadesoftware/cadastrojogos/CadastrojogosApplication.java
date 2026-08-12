package com.arquiteturadesoftware.cadastrojogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableFeignClients
@SpringBootApplication
public class CadastrojogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastrojogosApplication.class, args);
	}

}
