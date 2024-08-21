package System.App.controllers;

import System.App.entities.DeleteToken;
import System.App.entities.User;
import System.App.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("")
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @RequestMapping("/active")
    public List<User> getAllUsersActive(){
        return userRepository.getAllActive();
    }

    @GetMapping("/{id}")
    Optional<User> getUsers(@PathVariable int id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            return optional;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void createUser(@RequestBody User user) {
        userRepository.create(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    public void updateUser(@RequestBody User user) {
        userRepository.update(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/deactivate")
    public void deleteUser(@RequestBody DeleteToken token) {
        if(token.approved()){
            userRepository.deactivate(token.id());
            return;
        }
        else if(!token.approved()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id " + token.id() + " not found.");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void physicalDelete(@RequestBody DeleteToken token) {
        if(token.approved()){
            userRepository.delete(token.id());
            return;
        }
        else if(!token.approved()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id " + token.id() + " not found.");
    }
}
