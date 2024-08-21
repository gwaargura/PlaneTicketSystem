package System.App.controllers;

import System.App.entities.DeleteToken;
import System.App.entities.Plane;
import System.App.repositories.PlaneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/planes")
public class PlaneController {
    private final PlaneRepository planeRepository;
    public PlaneController(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping("")
    public List<Plane> getAll() {
        return planeRepository.getAll();
    }

    @GetMapping("/active")
    public List<Plane> getAllActive() {
        return planeRepository.getAllActive();
    }

    @GetMapping("/{brand}")
    public List<Plane> getInBrand(@PathVariable String brand) {
        List<Plane> list =  planeRepository.getInBrand(brand);
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No plane found with brand: " + brand);
        }
        return list;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void newPlane(@RequestBody Plane plane) {
        planeRepository.create(plane);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    public void updatePlane(@RequestBody Plane plane) {
        planeRepository.update(plane);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/deactivate")
    public void deactivatePlane(@RequestBody DeleteToken token) {
        if(token.approved()){
            planeRepository.deactivate(token.id());
            return;
        }
        else if(!token.approved()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No plane found with id: " + token.id());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void deletePlane(@RequestBody DeleteToken token) {
        if(token.approved()){
            planeRepository.delete(token.id());
            return;
        }
        else if(!token.approved()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Deletion not allowed");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No plane found with id: " + token.id());
    }
}
