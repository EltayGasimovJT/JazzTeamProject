package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.WorkerRepository;
import org.jazzteam.eltay.gasimov.service.*;
import org.jazzteam.eltay.gasimov.validator.WorkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@Slf4j
@Service(value = "userService")
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private WorkerRolesService workerRolesService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OrderService orderService;

    @Override
    public Worker save(WorkerDto workerDtoToSave) {
        Worker workerToSave = CustomModelMapper.mapDtoToWorker(workerDtoToSave);
        WorkerValidator.validateOnSave(workerToSave);

        return workerRepository.save(workerToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Worker> foundClientFromRepository = workerRepository.findById(idForDelete);
        Worker orderToUpdate = foundClientFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(orderToUpdate);
        workerRepository.deleteById(idForDelete);
    }

    @Override
    public List<Worker> findAll() throws IllegalArgumentException {
        List<Worker> usersFromRepository = workerRepository.findAll();
        WorkerValidator.validateUsersList(usersFromRepository);
        return usersFromRepository;
    }

    @Override
    public Worker findOne(long idForSearch) throws IllegalArgumentException {
        Optional<Worker> foundWorkerFromRepository = workerRepository.findById(idForSearch);
        Worker foundWorker = foundWorkerFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(foundWorker);
        return foundWorker;
    }

    @Override
    public Worker update(WorkerDto workerDtoToUpdate) {
        Worker workerToUpdate = CustomModelMapper.mapDtoToWorker(workerDtoToUpdate);
        WorkerValidator.validateUser(workerToUpdate);
        return workerRepository.save(workerToUpdate);
    }

    @Override
    public Worker changeWorkingPlace(Long id, AbstractBuildingDto newWorkingPlace) throws IllegalArgumentException {
        Optional<Worker> foundUser = workerRepository.findById(id);
        Worker workerFromOptional = foundUser.orElseGet(Worker::new);

        if (newWorkingPlace instanceof OrderProcessingPointDto) {
            AbstractBuilding abstractBuildingToUpdate = new OrderProcessingPoint();
            abstractBuildingToUpdate.setId(newWorkingPlace.getId());
            abstractBuildingToUpdate.setLocation(newWorkingPlace.getLocation());
            abstractBuildingToUpdate.setWorkingPlaceType(newWorkingPlace.getWorkingPlaceType().toString());
            workerFromOptional.setWorkingPlace(abstractBuildingToUpdate);
        } else if (newWorkingPlace instanceof WarehouseDto) {
            AbstractBuilding abstractBuildingToUpdate = new Warehouse();
            abstractBuildingToUpdate.setId(newWorkingPlace.getId());
            abstractBuildingToUpdate.setLocation(newWorkingPlace.getLocation());
            abstractBuildingToUpdate.setWorkingPlaceType(newWorkingPlace.getWorkingPlaceType().toString());
            workerFromOptional.setWorkingPlace(abstractBuildingToUpdate);
        }

        return update(CustomModelMapper.mapWorkerToDto(workerFromOptional));
    }

    @Override
    public Worker findByName(String name) {
        Optional<Worker> foundClientFromRepository = workerRepository.findByName(name);
        Worker foundWorker = foundClientFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(foundWorker);
        return foundWorker;
    }

    @Override
    public Worker findByLoginAndPassword(String login, String password) {
        Worker workerEntity = findByName(login);
        if (workerEntity != null) {
            if (passwordEncoder.matches(password, workerEntity.getPassword())) {
                return workerEntity;
            }
        }
        return null;
    }

    @Override
    public Worker findByPassword(String password) {
        Optional<Worker> foundClientFromRepository = workerRepository.findByPassword(password);
        Worker foundWorker = foundClientFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(foundWorker);
        return foundWorker;
    }

    @Override
    public Worker saveForRegistration(RegistrationRequest registrationRequest) {
        Worker workerToSave = Worker.builder()
                .name(registrationRequest.getLogin())
                .surname(registrationRequest.getSurname())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(
                        Stream.of(workerRolesService.findByRole(registrationRequest.getRole()))
                                .collect(Collectors.toSet())
                )
                .build();
        if (registrationRequest.getWorkingPlaceType().equals("WAREHOUSE")) {
            workerToSave.setWorkingPlace(warehouseService.findOne(registrationRequest.getWorkingPlaceId()));
        }
        if (registrationRequest.getWorkingPlaceType().equals("PROCESSING_POINT")) {
            workerToSave.setWorkingPlace(processingPointService.findOne(registrationRequest.getWorkingPlaceId()));
        }
        return workerRepository.save(workerToSave);
    }

    @Override
    public Set<WorkerRoles> findWorkerRoles(String username) {
        Worker foundWorker = findByName(username);
        return foundWorker.getRoles();
    }

    @Override
    public String findStatesByRole(Worker foundByName, String orderNumber) {
        List<OrderState> allStatesFromRepository = orderStateService.findAll();
        Order foundOrder = orderService.findByTrackNumber(orderNumber);

        for (OrderState orderState : allStatesFromRepository) {
            if (foundOrder.getState().equals(orderState)) {
                if (foundByName.getRoles().iterator().next().getRole().equals(Role.ROLE_WAREHOUSE_WORKER.name())
                        && (foundOrder.getState().getId() > SEVEN && foundOrder.getState().getId() < FOUR)) {
                    throw new IllegalStateException(WAREHOUSE_NOT_ALLOWED_STATE_CHANGING_MESSAGE);
                }
                return orderStateService.findOne(foundOrder.getState().getNextStateId()).getState();
            }
        }
        return null;
    }
}