package System.App.Controllers;

import System.App.Entities.User;
import System.App.Repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/users")
    List<User> getUsers() {
        return userRepository.findAll();
    }
}
