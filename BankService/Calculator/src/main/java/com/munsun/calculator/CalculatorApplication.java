package com.munsun.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableDiscoveryClient
public class CalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}
}