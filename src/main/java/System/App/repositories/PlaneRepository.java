package System.App.repositories;

import System.App.entities.Plane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class PlaneRepository {
    private static final Logger log = LoggerFactory.getLogger(PlaneRepository.class);
    private final JdbcClient jdbcClient;

    public PlaneRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Plane> getAll() {
        return jdbcClient
                .sql("Select * from planes")
                .query(Plane.class)
                .list();
    }

    public List<Plane> getAllActive(){
        return jdbcClient
                .sql("select * from planes where active=true")
                .query(Plane.class)
                .list();
    }

    public List<Plane> getInBrand(String brand){
        return jdbcClient
                .sql("select * from planes where brand ILIKE ?")
                .param("%" + brand + "%")
                .query(Plane.class)
                .list();
    }

    public void create(Plane plane){
        var created = jdbcClient
                .sql("insert into planes (brand, name, size, active) values (?, ?, ?, ?)")
                .param(plane.brand())
                .param(plane.name())
                .param(plane.size())
                .param(plane.active())
                .update();

        Assert.state(created == 1, "Failed to create plane");
    }

    public void update(Plane plane){
        var updated = jdbcClient
                .sql("update planes set brand = ?, name = ?, size = ?, active = ? where id = ?")
                .param(plane.brand())
                .param(plane.name())
                .param(plane.size())
                .param(plane.active())
                .param(plane.id())
                .update();

        Assert.state(updated == 1, "Failed to update plane");
    }

    public void deactivate (int id){
        var deactivated = jdbcClient
                .sql("update planes set active = false where id = ?")
                .param(id)
                .update();

        Assert.state(deactivated == 1, "Failed to deactivate plane");
    }

    public void delete (int id){
        var deleted = jdbcClient
                .sql("delete from planes where id = ?")
                .param(id)
                .update();

        Assert.state(deleted == 1, "Failed to delete plane");
    }

    public int count(){
        return jdbcClient
                .sql("SELECT * from planes")
                .query(Plane.class)
                .list()
                .size();
    }

    public void saveAll(List<Plane> planes){
        planes.stream().forEach(this::create);
    }


}
