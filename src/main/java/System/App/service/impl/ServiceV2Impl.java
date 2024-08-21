package System.App.service.impl;

import System.App.service.ServiceInterface;

import java.util.List;

public class ServiceV2Impl implements ServiceInterface {
    @Override
    public <T> void create(T input) {

    }

    @Override
    public <T> void update(T input) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public <T> T getById(int id) {
        return null;
    }

    @Override
    public <T> List<T> getAll() {
        return List.of();
    }
}
