package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Worker;

import java.util.List;

public interface WorkerService {
    Worker save(WorkerDto userToSave);

    void delete(Long idForDelete);

    List<Worker> findAll();

    Worker findOne(long idForSearch);

    Worker update(WorkerDto workerDtoToUpdate);

    Worker changeWorkingPlace(Long userId, AbstractBuildingDto newWorkingPlace);

    Worker findByName(String name);

    Worker findByLoginAndPassword(String login, String password);

    Worker findByPassword(String password);

    Worker saveForRegistration(RegistrationRequest registrationRequest);
}