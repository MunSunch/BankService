package com.munsun.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableFeignClients
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DealApplication {
	public static void main(String[] args) {
		SpringApplication.run(DealApplication.class, args);
	}
}