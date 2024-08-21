package System.App;

import System.App.repositories.FlightRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootApplication
public class PlaneTicketSystemApplication {

	Logger log = LoggerFactory.getLogger(PlaneTicketSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PlaneTicketSystemApplication.class, args);
	}

}
