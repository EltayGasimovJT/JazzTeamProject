package service;

import dto.VoyageDto;
import entity.Voyage;

import java.sql.SQLException;
import java.util.List;

public interface VoyageService {
    VoyageDto addVoyage(VoyageDto voyage) throws SQLException;

    void deleteVoyage(Long id);

    List<VoyageDto> findAllVoyages() throws SQLException;

    VoyageDto getVoyage(long id) throws SQLException;

    VoyageDto update(VoyageDto voyage) throws SQLException;
}
