package service;

import entity.Voyage;

import java.sql.SQLException;
import java.util.List;

public interface VoyageService {
    Voyage addVoyage(Voyage voyage) throws SQLException;

    void deleteVoyage(Voyage voyage);

    List<Voyage> findAllVoyages() throws SQLException;

    Voyage getVoyage(long id) throws SQLException;

    Voyage update(Voyage voyage) throws SQLException;
}
