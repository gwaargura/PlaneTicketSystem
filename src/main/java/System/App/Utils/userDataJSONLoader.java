package System.App.Utils;

import System.App.Lists.Users;
import System.App.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class userDataJSONLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(userDataJSONLoader.class);
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public userDataJSONLoader(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/dataJSON/users.json")){
                Users users = objectMapper.readValue(inputStream, Users.class);
                log.info("Reading {} users from users JSON file.", users.users().size());
                userRepository.saveAll(users.users());
            }catch (IOException e){
                throw new RuntimeException("Failed to read users from users JSON file.", e);
            }
        }
        else{
            log.info("Not loading from JSON file. Collection already contains data.");
        }
    }
}
