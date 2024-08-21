package System.App.repositories;

import System.App.entities.Pilot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class PilotRepository {
    private static final Logger log = LoggerFactory.getLogger(PilotRepository.class);
    private final JdbcClient jdbcClient;

    public PilotRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Pilot> findById(int id) {
        return jdbcClient.sql("SELECT * from pilots where id = ?")
                .param(id)
                .query(Pilot.class)
                .optional();
    }

    public List<Pilot> getAll() {
        return jdbcClient.sql("select * from pilots")
                .query(Pilot.class)
                .list();
    }

    public List<Pilot> getAllActive() {
        return jdbcClient.sql("select * from pilots where active = true")
                .query(Pilot.class)
                .list();
    }

    public Optional<Pilot> getPilotById(int id) {
        return jdbcClient.sql("select * from pilots where id = ?")
                .param(id)
                .query(Pilot.class)
                .optional();
    }

    public Optional<Pilot> getPilotByName(String name) {
        return jdbcClient.sql("SELECT * FROM pilots WHERE name ILIKE ?")
                .param("%" + name + "%")  // Using % for wildcard search
                .query(Pilot.class)
                .optional();
    }

    public void create(Pilot pilot) {
        var created = jdbcClient.sql("INSERT INTO pilots (name, age, totalHourFlew, active) VALUES (?, ?, ?, ?)")
                .param(pilot.name())
                .param(pilot.age())
                .param(pilot.totalHourFlew())
                .param(pilot.active())
                .update();

        Assert.state(created == 1, "Failed to create pilot");
    }

    public void update(Pilot pilot){
        var updated = jdbcClient
                .sql("UPDATE pilots SET name = ?, age = ?, totalHourFlew = ?, active = ? WHERE id = ?")
                .param(pilot.name())
                .param(pilot.age())
                .param(pilot.totalHourFlew())
                .param(pilot.active())
                .param(pilot.id())
                .update();

        Assert.state(updated == 1, "Failed to update pilot");
    }

    public void updateHourFlew(Pilot pilot, int hourFlew) {
        var updated = jdbcClient
                .sql("UPDATE pilots SET totalHourFlew = ? WHERE id = ?")
                .param(pilot.totalHourFlew() + hourFlew)
                .param(pilot.id())
                .update();

        Assert.state(updated == 1, "Failed to update pilot total hour flew");
    }

    public void deactivate(int id) {
        var deactivated = jdbcClient
                .sql("UPDATE Pilots set active=false WHERE id=?")
                .param(id)
                .update();

        Assert.state(deactivated == 1, "Failed to deactivate pilot");
    }

    public int count(){
        return jdbcClient.sql("select * from pilots")
                .query(Pilot.class)
                .list()
                .size();
    }

    public void saveAll(List<Pilot> pilots) {
        pilots.stream().forEach(this::create);
    }

    public void delete(int id) {
        var deleted = jdbcClient
                .sql("DELETE FROM Pilots WHERE id=?")
                .param(id)
                .update();
        Assert.state(deleted == 1, "Failed to delete pilot");
    }

}
