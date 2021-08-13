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
    public void delete(Long id) {
        voyages.remove(findOne(id));
    }

    @Override
    public List<Voyage> findAll() {
        return voyages;
    }

    @Override
    public Voyage findOne(Long id) {
        return voyages.stream()
                .filter(voyage -> voyage.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Voyage update(Voyage update) {
        Voyage voyage = findOne(update.getId());
        voyages.remove(voyage);
        voyage.setDeparturePoint(update.getDeparturePoint());
        voyage.setDestinationPoint(update.getDestinationPoint());
        voyage.setSendingTime(update.getSendingTime());
        voyage.setDispatchedOrders(update.getDispatchedOrders());
        voyage.setExpectedOrders(update.getExpectedOrders());
        voyages.add(voyage);
        return voyage;
    }
}