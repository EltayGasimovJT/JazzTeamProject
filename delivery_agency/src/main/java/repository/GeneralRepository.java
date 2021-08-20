package repository;

import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {
    T save(T toSave) throws SQLException;

    void delete(Long idForDelete) throws SQLException;

    List<T> findAll() throws SQLException;

    T findOne(Long idForSearch) throws SQLException;

    T update(T update) throws SQLException;
}