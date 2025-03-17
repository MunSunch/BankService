package com.munsun.audit.utils;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class LoadTestApplicationContext {
	@Container
	private static RedisContainer redisTestContainer = new RedisContainer("redis:7.4.2-alpine")
			.withExposedPorts(6379)
			.withReuse(true)
			.withCommand("--requirepass password");

	@BeforeAll
	public static void start() {
		redisTestContainer.start();
	}

	@AfterAll
	public static void stop() {
		redisTestContainer.stop();
	}

	@DynamicPropertySource
	public static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", redisTestContainer::getHost);
		registry.add("spring.data.redis.port", () -> redisTestContainer.getFirstMappedPort().toString());
		registry.add("spring.data.redis.password", () -> "password");
	}
}
