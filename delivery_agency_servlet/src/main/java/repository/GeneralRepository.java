package repository;

import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {
    T save(T t) throws SQLException;

    void delete(T t) throws SQLException;

    List<T> findAll() throws SQLException;

    T findOne(Long id) throws SQLException;

    T update(T update) throws SQLException;
}
