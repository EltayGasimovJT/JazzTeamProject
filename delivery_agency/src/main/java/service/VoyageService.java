package service;

import dto.VoyageDto;
import entity.Voyage;

import java.sql.SQLException;
import java.util.List;

public interface VoyageService {
    Voyage save(VoyageDto voyageDtoToSave) throws SQLException;

    void delete(Long idForDelete);

    List<Voyage> findAll() throws SQLException;

    Voyage findOne(long idForSearch) throws SQLException;

    Voyage update(VoyageDto voyageToUpdate) throws SQLException;
}