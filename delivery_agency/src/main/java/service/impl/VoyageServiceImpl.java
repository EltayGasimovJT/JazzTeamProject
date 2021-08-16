package service.impl;

import dto.OrderDto;
import dto.VoyageDto;
import entity.Order;
import entity.Voyage;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import service.VoyageService;

import java.util.ArrayList;
import java.util.List;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public Voyage save(VoyageDto voyage) {
        List<Order> expectedOrdersToSave = new ArrayList<>();
        List<Order> dispatchedOrdersToSave = new ArrayList<>();

        for (OrderDto dispatchedOrder : voyage.getDispatchedOrders()) {
            Order orderToDispatch = Order.builder()
                    .id(dispatchedOrder.getId())
                    .build();

            dispatchedOrdersToSave.add(orderToDispatch);
        }

        for (OrderDto expectedOrder : voyage.getExpectedOrders()) {
            Order expectedOrderToSave = Order.builder()
                    .id(expectedOrder.getId())
                    .build();
            expectedOrdersToSave.add(expectedOrderToSave);
        }

        Voyage voyageToSave = new Voyage();
        voyageToSave.setId(voyage.getId());
        voyageToSave.setExpectedOrders(expectedOrdersToSave);
        voyageToSave.setDispatchedOrders(dispatchedOrdersToSave);
        voyageToSave.setDeparturePoint(voyage.getDeparturePoint());
        voyageToSave.setDestinationPoint(voyage.getDestinationPoint());
        voyageToSave.setSendingTime(voyage.getSendingTime());
        return voyageRepository.save(voyageToSave);
    }

    @Override
    public void delete(Long id) {
        if(voyageRepository.findOne(id) == null){
            throw new IllegalArgumentException("There is no voyage with this Id!!! Cannot delete this voyage");
        }
        voyageRepository.delete(id);
    }

    @Override
    public List<Voyage> findAll() {
        List<Voyage> voyagesFromRepository = voyageRepository.findAll();
        if (voyagesFromRepository.isEmpty()) {
            throw new IllegalArgumentException("There is no Voyages in database!!!");
        }
        return voyagesFromRepository;
    }

    @Override
    public Voyage findOne(long id) {
        Voyage voyage = voyageRepository.findOne(id);
        if(voyageRepository.findOne(id) == null){
            throw new IllegalArgumentException("There is no voyage with this Id!!! Cannot find this voyage");
        }

        return voyage;
    }

    @Override
    public Voyage update(VoyageDto voyage) {
        Voyage voyageToUpdate = new Voyage();
        voyageToUpdate.setId(voyage.getId());
        voyageToUpdate.setSendingTime(voyage.getSendingTime());
        voyageToUpdate.setDestinationPoint(voyage.getDestinationPoint());
        voyageToUpdate.setDeparturePoint(voyage.getDeparturePoint());
        return voyageRepository.update(voyageToUpdate);
    }
}