package emx.solar.pack;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import emx.solar.pack.configuration.JpaConfiguration;

@Import(JpaConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class SolarbootApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SolarbootApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		
		/*Arrays.asList("PHP", ".Net", "Spring").forEach(t -> {
			taskDao.persist(new Task(null, t));
		});*/
			
	}

}
