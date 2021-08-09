package service.impl;

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
    public Voyage findOne(Long id) {
        return voyages.stream()
                .filter(voyage -> voyage.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Voyage update(Voyage update) {
        Voyage actual = findOne(update.getId());
        voyages.remove(actual);
        actual.setDeparturePoint(update.getDeparturePoint());
        actual.setDestinationPoint(update.getDestinationPoint());
        actual.setSendingTime(update.getSendingTime());
        actual.setDispatchedOrders(update.getDispatchedOrders());
        actual.setExpectedOrders(update.getExpectedOrders());
        voyages.add(actual);
        return actual;
    }
}
