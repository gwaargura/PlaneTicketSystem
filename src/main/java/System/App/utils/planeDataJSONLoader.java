package System.App.utils;

import System.App.lists.Planes;
import System.App.repositories.PlaneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class planeDataJSONLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(planeDataJSONLoader.class);
    private final PlaneRepository planeRepository;
    private final ObjectMapper objectMapper;

    public planeDataJSONLoader(PlaneRepository planeRepository, ObjectMapper objectMapper) {
        this.planeRepository = planeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(planeRepository.count() == 0) {
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/dataJSON/planes.json")){
                Planes allPlanes = objectMapper.readValue(inputStream, Planes.class);
                log.info("Reading {} planes from planes JSON file.", allPlanes.planes().size());
                planeRepository.saveAll(allPlanes.planes());
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to read planes from planes JSON file.", e);
            }
        }
        else{
            log.info("Not loading planes from JSON file. Collection already contains data.");
        }
    }
}
