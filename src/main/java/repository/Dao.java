package repository;

import java.util.List;

public interface Dao<T> {

    T save(int id);
    T update(T toUpdate);
    T delete(T toDelete);
    T get(int id);
    List<T> getAll();
}
