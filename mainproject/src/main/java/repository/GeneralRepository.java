package repository;

import java.util.List;

public interface GeneralRepository<T> {
    T save(T t);

    void delete(T t);

    List<T> findAll();

    T findOne(long string);
}
