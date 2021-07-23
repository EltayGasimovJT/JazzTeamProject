package repository.impl;

import entity.Voyage;
import repository.VoyageRepository;

import java.util.ArrayList;
import java.util.List;

public class VoyageRepositoryImpl implements VoyageRepository {
    private final List<Voyage> voyages = new ArrayList<>();


    @Override
    public Voyage save(Voyage voyage) {
        voyages.add(voyage);
        return voyage;
    }

    @Override
    public void delete(Voyage voyage) {
        voyages.remove(voyage);
    }

    @Override
    public List<Voyage> findAll() {
        return voyages;
    }

    @Override
    public Voyage findOne(long id) {
        return voyages.stream()
                .filter(voyage -> voyage.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
