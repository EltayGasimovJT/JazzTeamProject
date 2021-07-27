package service.impl;

import entity.*;
import lombok.extern.slf4j.Slf4j;
import repository.OrderRepository;
import repository.WareHouseRepository;
import repository.impl.OrderRepositoryImpl;
import repository.impl.WareHouseRepositoryImpl;
import service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final WareHouseRepository wareHouseRepository = new WareHouseRepositoryImpl();


    @Override
    public Client.Order UpdateOrderCurrentLocation(long id, AbstractLocation newLocation) {
        //Client.Order order = new Client.Order();
        return null;
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) {

    }

    @Override
    public Client.Order create(Client.Order order) {
        BigDecimal price = calculatePrice(order);
        order.setPrise(price);
        return orderRepository.save(order);
    }

    @Override
    public Client.Order findById(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Client.Order findByRecipient(Client recipient) {
        return orderRepository.findByRecipient(recipient);
    }

    @Override
    public Client.Order findBySender(Client sender) {
        return orderRepository.findBySender(sender);
    }

    @Override
    public AbstractLocation getCurrentOrderLocation(long id) {
        Client.Order order = orderRepository.findOne(id);
        return order.getCurrentLocation();
    }

    @Override
    public void send(List<Client.Order> orders, Voyage voyage) {
        for (Client.Order order : orders) {
            freeSpace(order);
            order.setCurrentLocation(voyage);
        }
        orderRepository.saveSentOrders(orders);
    }

    private void freeSpace(Client.Order order) {
        wareHouseRepository.findOne(order.getCurrentLocation().getId())
                .getStillages()
                .get()
    }

    @Override
    public void accept(List<Client.Order> orders) {
        List<Client.Order> orders1 = orderRepository.acceptOrders(orders);
        for (Client.Order order : orders1) {
            findPlaceForOrder(order);
            log.info(order.toString());
        }
    }

    @Override
    public String getState(long id) {
        Client.Order order = orderRepository.findOne(id);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<Client.Order> expectedOrders, List<Client.Order> acceptedOrders) throws IllegalArgumentException {
        for (Client.Order expectedOrder : expectedOrders) {
            if (!expectedOrder.equals(acceptedOrders)) {
                throw new IllegalArgumentException("Expected List is not equal to actual!!!");
            }
        }
    }

    private Client.Order findPlaceForOrder(Client.Order order) {
        Warehouse warehouse = wareHouseRepository.findOne(order.getCurrentLocation().getId());
        List<Stillage> stillages = warehouse.getStillages();
        for (Stillage stillage : stillages) {
            if (stillage.getCountOfFreeSpaces() != 0) {
                takeSpace(order, stillage);
            }
        }
        return order;
    }

    private void takeSpace(Client.Order order, Stillage stillage) {
        order.setStillageSpaceId(stillage.getId());
    }

    @Override
    public boolean isFinalWarehouse(Client.Order order) {
        return false;
    }

    @Override
    public long getTracker(Client.Order order) {
        return 0;
    }

    private BigDecimal calculatePrice(Client.Order order) throws IllegalArgumentException {
        BigDecimal decimal = BigDecimal.valueOf(1);
        BigDecimal resultPrice;
        String location = order.getDestinationPlace().getLocation();
        switch (location) {
            case "Moskov": {
                resultPrice = decimal.multiply(new BigDecimal((20 * 10) / 5));
                return resultPrice;
            }
            case "Poland": {
                resultPrice = decimal.multiply(new BigDecimal((30 * 10) / 5));
                return resultPrice;
            }
            case "Ukraine": {
                resultPrice = decimal.multiply(new BigDecimal((40 * 10) / 5));
                return resultPrice;
            }
            default: {
                throw new IllegalArgumentException("Country is not supported Yet!!!");
            }
        }
    }
}
