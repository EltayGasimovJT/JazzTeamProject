package service.impl;

import dto.AbstractLocationDto;
import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.fromDtoToOrderProcessingPoint;
import static util.ConvertUtil.fromOrderProcessingPointToDTO;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override
    public OrderProcessingPointDto addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = fromDtoToOrderProcessingPoint(orderProcessingPointDto);
        return fromOrderProcessingPointToDTO(orderProcessingPointRepository.save(orderProcessingPoint));
    }

    @Override
    public void deleteOrderProcessingPoint(Long id) {
        orderProcessingPointRepository.delete(id);
    }

    @Override
    public List<OrderProcessingPointDto> findAllOrderProcessingPoints() {
        List<OrderProcessingPoint> processingPoints = orderProcessingPointRepository.findAll();
        List<OrderProcessingPointDto> processingPointDto = new ArrayList<>();
        for (OrderProcessingPoint processingPoint : processingPoints) {
            OrderProcessingPointDto orderProcessingPointDto = fromOrderProcessingPointToDTO(processingPoint);
            processingPointDto.add(orderProcessingPointDto);
        }
        return processingPointDto;
    }

    @Override
    public OrderProcessingPointDto getOrderProcessingPoint(long id) {
        return fromOrderProcessingPointToDTO(orderProcessingPointRepository.findOne(id));
    }

    @Override
    public OrderProcessingPointDto update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException {
        OrderProcessingPoint update = orderProcessingPointRepository.update(fromDtoToOrderProcessingPoint(orderProcessingPointDto));
        return fromOrderProcessingPointToDTO(update);
    }

}