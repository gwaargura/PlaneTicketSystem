package System.App.Repositories;

import System.App.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> findAll() {
        return jdbcClient.sql("Select * from users")
                .query(User.class)
                .list();
    }

    public int count(){
        return jdbcClient.sql("select * from users where active=true")
                .query()
                .listOfRows()
                .size();
    }
    public void create(User user) {
        var updated = jdbcClient.sql("INSERT INTO users (id, name, password, email, phone, active) VALUES (?, ?, ?, ?, ?, ?)")
                .param(user.id())
                .param(user.name())
                .param(user.password())
                .param(user.email())
                .param(user.phone())
                .param(user.active())
                .update();

        Assert.state(updated == 1, "Failed to create user " + user.id() + ": " + user.name());
    }


    public void saveAll(List<User> users) {
        users.stream().forEach(this::create);
    }
}
