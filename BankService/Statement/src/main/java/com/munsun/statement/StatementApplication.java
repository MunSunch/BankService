package com.munsun.statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableFeignClients
@SpringBootApplication
public class StatementApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatementApplication.class, args);
	}
}