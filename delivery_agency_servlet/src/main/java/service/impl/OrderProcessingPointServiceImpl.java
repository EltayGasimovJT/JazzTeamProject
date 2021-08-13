package service.impl;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override
    public OrderProcessingPointDto addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) throws SQLException {
        OrderProcessingPoint orderProcessingPoint = fromDtoToOrderProcessingPoint(orderProcessingPointDto);
        return fromOrderProcessingPointToDTO(orderProcessingPointRepository.save(orderProcessingPoint));
    }

    @Override
    public void deleteOrderProcessingPoint(Long id) throws SQLException {
        orderProcessingPointRepository.delete(id);
    }

    @Override
    public List<OrderProcessingPointDto> findAllOrderProcessingPoints() throws SQLException {
        List<OrderProcessingPoint> processingPoints = orderProcessingPointRepository.findAll();
        List<OrderProcessingPointDto> processingPointDto = new ArrayList<>();
        for (OrderProcessingPoint processingPoint : processingPoints) {
            OrderProcessingPointDto orderProcessingPointDto = fromOrderProcessingPointToDTO(processingPoint);
            processingPointDto.add(orderProcessingPointDto);
        }
        return processingPointDto;
    }

    @Override
    public OrderProcessingPointDto getOrderProcessingPoint(long id) throws SQLException {
        return fromOrderProcessingPointToDTO(orderProcessingPointRepository.findOne(id));
    }

    @Override
    public OrderProcessingPointDto update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException {
        OrderProcessingPoint update = orderProcessingPointRepository.update(fromDtoToOrderProcessingPoint(orderProcessingPointDto));
        return fromOrderProcessingPointToDTO(update);
    }

    private OrderProcessingPointDto fromOrderProcessingPointToDTO(OrderProcessingPoint orderProcessingPoint) {
        return OrderProcessingPointDto.builder()
                .id(orderProcessingPoint.getId())
                .dispatchedOrders(orderProcessingPoint.getDispatchedOrders())
                .expectedOrders(orderProcessingPoint.getExpectedOrders())
                .location(orderProcessingPoint.getLocation())
                .warehouse(orderProcessingPoint.getWarehouse())
                .build();
    }

    private OrderProcessingPoint fromDtoToOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setWarehouse(orderProcessingPointDto.getWarehouse());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        orderProcessingPoint.setDispatchedOrders(orderProcessingPointDto.getDispatchedOrders());
        orderProcessingPoint.setExpectedOrders(orderProcessingPointDto.getExpectedOrders());
        return orderProcessingPoint;
    }
}
