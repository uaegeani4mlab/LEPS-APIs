package eu.leps.LinkingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LinkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkingServiceApplication.class, args);
	}
}
