package servicetest.impl;

import entity.Voyage;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import servicetest.VoyageService;

import java.util.List;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public Voyage addVoyage(Voyage voyage) {
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteVoyage(Voyage voyage) {
        voyageRepository.delete(voyage);
    }

    @Override
    public List<Voyage> findAllVoyages() {
        return voyageRepository.findAll();
    }

    @Override
    public Voyage getVoyage(long id) {
        return voyageRepository.findOne(id);
    }

    @Override
    public Voyage update(Voyage voyage) {
        return voyageRepository.update(voyage);
    }
}
