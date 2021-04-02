package org.example.model.jdbc;

import org.example.model.menu.MenuItem;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(T t);
    //void addFromCSV();
    Optional<T> findById(int id);
    void update(T t);
    void delete(T t);
    List<MenuItem> getAll();

}
