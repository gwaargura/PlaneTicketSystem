package System.App.repositories;

import System.App.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> getAll() {
        return jdbcClient
                .sql("Select * from users")
                .query(User.class)
                .list();
    }

    public List<User> getAllActive() {
        return jdbcClient
                .sql("Select * from users where active = true")
                .query(User.class)
                .list();
    }

    public int count(){
        return jdbcClient
                .sql("select * from users where active=true")
                .query()
                .listOfRows()
                .size();
    }

    public Optional<User> findById(int id) {
        return jdbcClient
                .sql("select * from users where id = ?")
                .param(id)
                .query(User.class)
                .optional();

    }

    public void create(User user) {
        var updated = jdbcClient
                .sql("INSERT INTO users (name, password, email, phone, active) VALUES (?, ?, ?, ?, ?)")
                .param(user.name())
                .param(user.password())
                .param(user.email())
                .param(user.phone())
                .param(user.active())
                .update();

        Assert.state(updated == 1, "Failed to create user");
    }

    public void update(User user) {
        var updated = jdbcClient
                .sql("UPDATE users set name=?, password=?, email=?, phone=?, active=? WHERE id=?")
                .param(user.name())
                .param(user.password())
                .param(user.email())
                .param(user.phone())
                .param(user.active())
                .param(user.id())
                .update();

        Assert.state(updated == 1, "Failed to update user");
    }

    public void deactivate(int id) {
         var deactivated = jdbcClient
                 .sql("UPDATE users set active=false WHERE id=?")
                .param(id)
                .update();

        Assert.state(deactivated == 1, "Failed to deactivate user");
    }

    public void saveAll(List<User> users) {
        users.stream().forEach(this::create);
    }

    public void delete(int id) {
        var deleted = jdbcClient
                .sql("DELETE FROM users WHERE id=?")
                .param(id)
                .update();
        Assert.state(deleted == 1, "Failed to delete user");

    }
}
