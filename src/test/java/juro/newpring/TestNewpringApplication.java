package juro.newpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestNewpringApplication {

	@Bean
	@RestartScope
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>("mysql:latest");
	}

	public static void main(String[] args) {
		SpringApplication
			.from(NewpringApplication::main)
			.with(TestNewpringApplication.class)
			.run(args);
	}

}
