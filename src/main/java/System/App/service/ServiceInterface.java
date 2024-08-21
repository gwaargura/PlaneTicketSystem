package System.App.service;

import java.util.List;

public interface ServiceInterface {
    public <T> void create(T input);
    public <T> void update(T input);
    public void delete(int id);
    public <T> T getById(int id);
    public <T> List<T> getAll();
}
