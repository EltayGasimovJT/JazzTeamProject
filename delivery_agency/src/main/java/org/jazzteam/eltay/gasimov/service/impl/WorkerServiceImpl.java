package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.AbstractBuilding;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.UserRepository;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.jazzteam.eltay.gasimov.service.WorkerService;
import org.jazzteam.eltay.gasimov.validator.WorkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service(value = "userService")
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private UserRolesService userRolesService;

    @Override
    public Worker save(WorkerDto workerDtoToSave) {
        Worker workerToSave = CustomModelMapper.mapDtoToWorker(workerDtoToSave);
        WorkerValidator.validateOnSave(workerToSave);

        return userRepository.save(workerToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Worker> foundClientFromRepository = userRepository.findById(idForDelete);
        Worker orderToUpdate = foundClientFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(orderToUpdate);
        userRepository.deleteById(idForDelete);
    }

    @Override
    public List<Worker> findAll() throws IllegalArgumentException {
        List<Worker> usersFromRepository = userRepository.findAll();
        WorkerValidator.validateUsersList(usersFromRepository);
        return usersFromRepository;
    }

    @Override
    public Worker findOne(long idForSearch) throws IllegalArgumentException {
        Optional<Worker> foundWorkerFromRepository = userRepository.findById(idForSearch);
        Worker foundWorker = foundWorkerFromRepository.orElseGet(Worker::new);
        WorkerValidator.validateUser(foundWorker);
        return foundWorker;
    }

    @Override
    public Worker update(WorkerDto workerDtoToUpdate) {
        Worker workerToUpdate = CustomModelMapper.mapDtoToWorker(workerDtoToUpdate);
        WorkerValidator.validateUser(workerToUpdate);
        return userRepository.save(workerToUpdate);
    }

    @Override
    public Worker changeWorkingPlace(Long id, AbstractBuildingDto newWorkingPlace) throws IllegalArgumentException {
        Optional<Worker> foundUser = userRepository.findById(id);
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

        return update(CustomModelMapper.mapUserToDto(workerFromOptional));
    }

    @Override
    public Worker findByName(String name) {
        Optional<Worker> foundClientFromRepository = userRepository.findByName(name);
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
        Optional<Worker> foundClientFromRepository = userRepository.findByPassword(password);
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
                        Stream.of(userRolesService.findByRole(registrationRequest.getRole()))
                        .collect(Collectors.toSet())
                )
                .workingPlace(processingPointService.findOne(registrationRequest.getWorkingPlaceId()))
                .build();
        return userRepository.save(workerToSave);
    }
}