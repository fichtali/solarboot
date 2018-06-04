package emx.solar.pack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import emx.solar.pack.configuration.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication
public class SolarbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarbootApplication.class, args);
	}
}
