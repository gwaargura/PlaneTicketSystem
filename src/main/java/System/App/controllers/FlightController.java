package System.App.controllers;

import System.App.entities.Flight;
import System.App.repositories.FlightRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightRepository flightRepository;
    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/origin={origin}")
    public List<Flight> findByOrigin(@PathVariable String origin) {
        return flightRepository.getByOrigin(origin);
    }

    @GetMapping("/destination={destination}")
    public List<Flight> findByDestination(@PathVariable String destination) {
        return flightRepository.getByDestination(destination);
    }

    @GetMapping("/origin={origin}/destination={destination}")
    public List<Flight> findByOriginAndDestination(@PathVariable String origin, @PathVariable String destination) {
        return flightRepository.getByOriginAndDestination(origin, destination);
    }

}
