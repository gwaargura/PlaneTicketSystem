package System.App.utils;

import System.App.lists.Pilots;
import System.App.repositories.PilotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class pilotDataJSONLoader implements CommandLineRunner{
        private static final Logger log = LoggerFactory.getLogger(pilotDataJSONLoader.class);
        private final PilotRepository pilotRepository;
        private final ObjectMapper objectMapper;

        public pilotDataJSONLoader(PilotRepository pilotRepository, ObjectMapper objectMapper) {
            this.pilotRepository = pilotRepository;
            this.objectMapper = objectMapper;
        }

        @Override
        public void run(String... args) throws Exception {
            if(pilotRepository.count() == 0){
                try(InputStream inputStream = TypeReference.class.getResourceAsStream("/dataJSON/pilots.json")){
                    Pilots allPilots = objectMapper.readValue(inputStream, Pilots.class);
                    log.info("Reading {} pilots from pilots JSON file.", allPilots.pilots().size());
                    pilotRepository.saveAll(allPilots.pilots());
                }catch (IOException e){
                    throw new RuntimeException("Failed to read pilots from pilots JSON file.", e);
                }
            }
            else{
                log.info("Not loading pilots from JSON file. Collection already contains data.");
            }
        }
    }
