package repository;

import java.util.List;

public interface GeneralRepository<T> {
    T save(T t);

    void delete(Long id);

    List<T> findAll();

    T findOne(Long id);

    T update(T update);
}