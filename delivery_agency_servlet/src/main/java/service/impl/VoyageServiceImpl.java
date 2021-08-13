package service.impl;

import entity.Voyage;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import service.VoyageService;

import java.sql.SQLException;
import java.util.List;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public Voyage addVoyage(Voyage voyage) throws SQLException {
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteVoyage(Voyage voyage) throws SQLException {
        voyageRepository.delete(voyage.getId());
    }

    @Override
    public List<Voyage> findAllVoyages() throws SQLException {
        return voyageRepository.findAll();
    }

    @Override
    public Voyage getVoyage(long id) throws SQLException {
        return voyageRepository.findOne(id);
    }

    @Override
    public Voyage update(Voyage voyage) throws SQLException {
        return voyageRepository.update(voyage);
    }
}
