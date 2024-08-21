package System.App.controllers;

import System.App.entities.DeleteToken;
import System.App.entities.Pilot;
import System.App.repositories.PilotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pilots")
public class PilotController {
    private final PilotRepository pilotRepository;
    public PilotController(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }
    @RequestMapping("")
    public List<Pilot> getAll() {
        return pilotRepository.getAll();
    }
    @RequestMapping("/{id}")
    public Optional<Pilot> getPilotById(@PathVariable int id) {
        Optional<Pilot> pilot = pilotRepository.getPilotById(id);
        if (pilot.isPresent()) {
            return pilot;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pilot not found");
    }
    @RequestMapping("/active")
    public List<Pilot> getAllActive() {
        return pilotRepository.getAllActive();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void newPilot(@RequestBody Pilot pilot) {
        pilotRepository.create(pilot);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    public void updatePilot(@RequestBody Pilot pilot) {
        pilotRepository.update(pilot);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/deactivate")
    public void deletePilot(@RequestBody DeleteToken token) {
        if(token.approved()){
            pilotRepository.deactivate(token.id());
            return;
        }
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void physicalDeletePilot(@RequestBody DeleteToken token) {
        if(token.approved()){
            pilotRepository.delete(token.id());
            return;
        }
        else if(!token.approved()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pilot not found");
    }
}
