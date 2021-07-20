package repository;

import entity.Client;

import java.util.List;

public interface OrderRepository extends GeneralRepository<Client.Order> {
    List<Client.Order> findAllByClientId(Client.Order order);
}
