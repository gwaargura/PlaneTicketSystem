package System.App.repositories;

import System.App.entities.Flight;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class FlightRepository {
    private static final Logger log = LoggerFactory.getLogger(FlightRepository.class);
    private final JdbcClient jdbcClient;

//    @PostConstruct
//    public void init() {
//        for(Flight f : this.getByOriginAndDestination("Ha Noi", "Japan")){
//            log.info(f.toString());
//        }
//    }

    public FlightRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Flight> getAll() {
        return jdbcClient
                .sql("Select * from flights")
                .query(Flight.class)
                .list();
    }
    public List<Flight> getByOrigin(String origin) {
        return jdbcClient
                .sql("Select * from flights where origin ILIKE ?")
                .param("%" + origin + "%")
                .query(Flight.class)
                .list();
    }
    public List<Flight> getByDestination(String destination) {
        destination = "%" + destination + "%";
        return jdbcClient
                .sql("Select * from flights where destination ILIKE ?")
                .param(destination)
                .query(Flight.class)
                .list();
    }

    public List<Flight> getByOriginAndDestination(String origin, String destination) {
        origin = "%" + origin + "%";
        destination = "%" + destination + "%";
        return jdbcClient
                .sql("Select * from flights where origin ILIKE ? and destination ILIKE ?")
                .param(origin)
                .param(destination)
                .query(Flight.class)
                .list();
    }

    public Optional<Flight> getById(int id) {
        return jdbcClient
                .sql("Select * From flights where id = ?")
                .param(id)
                .query(Flight.class)
                .optional();
    }

    public List<Flight> getByBrand(String brand) {
        brand = "%" + brand + "%";
        return jdbcClient
                .sql("Select f.* from flights f left join planes p on p.id = f.planeId where p.brand ILIKE ?")
                .param(brand)
                .query(Flight.class)
                .list();
    }

    public void create(Flight flight) {
        var created = jdbcClient
                .sql("INSERT INTO flights (planeId, pilotId, copilotId, origin, destination, departureTime, duration) VALUES (?, ?, ?, ?, ?, ?, ?);")
                .param(flight.planeId())
                .param(flight.pilotId())
                .param(flight.copilotId())
                .param(flight.origin())
                .param(flight.destination())
                .param(flight.departureTime())
                .param(flight.duration())
                .update();

        Assert.state(created == 1, "Failed to create flight");
    }

    public void update(Flight flight) {
        var updated = jdbcClient
                .sql("""
                        UPDATE flights set planeId = ?, pilotId = ?, copilotId= ?,
                   origin= ?, destination= ?, departureTime= ?, duration= ? where id=?
                   """)
                .param(flight.planeId())
                .param(flight.pilotId())
                .param(flight.copilotId())
                .param(flight.origin())
                .param(flight.destination())
                .param(flight.departureTime())
                .param(flight.duration())
                .param(flight.id())
                .update();

        Assert.state(updated == 1, "Failed to update flight");
    }

    public void delete(int id) {
        var deleted = jdbcClient
                .sql("Delete from flights where id = ?")
                .param(id)
                .update();

        Assert.state(deleted == 1, "Failed to delete flight");
    }

    public int count() {
        return jdbcClient
                .sql("Select * from flights")
                .query(Flight.class)
                .list()
                .size();
    }

    public void saveAll(List<Flight> flights){
        flights.stream().forEach(this::create);
    }
}
