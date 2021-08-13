package service;

import dto.VoyageDto;
import entity.Voyage;

import java.sql.SQLException;
import java.util.List;

public interface VoyageService {
    Voyage save(VoyageDto voyage) throws SQLException;

    void delete(Long id);

    List<Voyage> findAll() throws SQLException;

    Voyage findOne(long id) throws SQLException;

    Voyage update(VoyageDto voyage) throws SQLException;
}