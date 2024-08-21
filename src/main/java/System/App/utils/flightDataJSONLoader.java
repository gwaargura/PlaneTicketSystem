package System.App.utils;

import System.App.lists.Flights;
import System.App.repositories.FlightRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class flightDataJSONLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(flightDataJSONLoader.class);
    private final FlightRepository flightRepository;
    private final ObjectMapper objectMapper;

    public flightDataJSONLoader(FlightRepository flightRepository, ObjectMapper objectMapper) {
        this.flightRepository = flightRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(flightRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/dataJSON/flights.json")){
                Flights allFlights = objectMapper.readValue(inputStream, Flights.class);
                log.info("Reading {} flights from flights JSON file.", allFlights.flights().size());
                flightRepository.saveAll(allFlights.flights());
            }
            catch(IOException e){
                log.error("Failed to read flights from flights JSON file.", e);
            }
        }
        else{
            log.info("Not loading flights from JSON file. Collection already contains data.");
        }
    }
}
