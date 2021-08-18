package repository.impl;

import entity.Voyage;
import repository.VoyageRepository;

import java.util.ArrayList;
import java.util.List;

public class VoyageRepositoryImpl implements VoyageRepository {
    private final List<Voyage> voyages = new ArrayList<>();

    @Override
    public Voyage save(Voyage voyageToSave) {
        voyages.add(voyageToSave);
        return voyageToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        voyages.remove(findOne(idForDelete));
    }

    @Override
    public List<Voyage> findAll() {
        return voyages;
    }

    @Override
    public Voyage findOne(Long idForSearch) {
        return voyages.stream()
                .filter(voyage -> voyage.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Voyage update(Voyage newVoyage) {
        Voyage voyageToUpdate = findOne(newVoyage.getId());
        voyages.remove(voyageToUpdate);
        voyageToUpdate.setDeparturePoint(newVoyage.getDeparturePoint());
        voyageToUpdate.setDestinationPoint(newVoyage.getDestinationPoint());
        voyageToUpdate.setSendingTime(newVoyage.getSendingTime());
        voyageToUpdate.setDispatchedOrders(newVoyage.getDispatchedOrders());
        voyageToUpdate.setExpectedOrders(newVoyage.getExpectedOrders());
        voyages.add(voyageToUpdate);
        return voyageToUpdate;
    }
}