package service.impl;

import entity.Voyage;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import service.VoyageService;

import java.util.List;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public Voyage addUser(Voyage voyage) {
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteUser(Voyage voyage) {
        voyageRepository.delete(voyage);
    }

    @Override
    public List<Voyage> findAllUsers() {
        return voyageRepository.findAll();
    }

    @Override
    public Voyage getUser(long id) {
        return voyageRepository.findOne(id);
    }

    @Override
    public Voyage update(Voyage voyage) {
        return voyageRepository.update(voyage);
    }
}
