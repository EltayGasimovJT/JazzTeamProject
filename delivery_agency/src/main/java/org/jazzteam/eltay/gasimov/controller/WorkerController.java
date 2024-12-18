package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.ContextService;
import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.jazzteam.eltay.gasimov.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@RestController
@Log
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ContextService contextService;
    @Autowired
    private OrderStateService orderStateService;

    @PostMapping(path = WORKERS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Worker save(@RequestBody WorkerDto workerDtoToSave) {
        return workerService.save(workerDtoToSave);
    }

    @PostMapping(path = WORKERS_CHANGE_WORKING_PLACE_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody
    Worker changeWorkingPlace(@PathVariable Long id, @RequestBody AbstractBuildingDto newWorkingPlace) {
        return workerService.changeWorkingPlace(id, newWorkingPlace.getId());
    }

    @GetMapping(path = WORKERS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Worker> findAll() {
        return workerService.findAll();
    }

    @DeleteMapping(path = WORKERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        workerService.delete(id);
    }

    @GetMapping(path = WORKERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerDto findById(@PathVariable Long id) {
        return CustomModelMapper.mapWorkerToDto(workerService.findOne(id));
    }

    @GetMapping(path = WORKERS_FIND_ROLES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<String> findRoles() {
        CustomUserDetails foundWorkerFromContext = contextService.getCurrentUserFromContext();
        final Set<WorkerRoles> foundRoles = workerService.findWorkerRoles(foundWorkerFromContext.getUsername());
        return foundRoles.stream()
                .map(WorkerRoles::getRole)
                .collect(Collectors.toSet());
    }

    @GetMapping(path = WORKERS_GET_STATES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String getStatesByOrderNumber(@RequestParam String orderNumber) {
        CustomUserDetails currentUserFromContext = contextService.getCurrentUserFromContext();
        Worker foundByName = workerService.findByName(currentUserFromContext.getUsername());
        return orderStateService.findStatesByRole(foundByName, orderNumber);
    }

    @GetMapping(path = WORKERS_GET_CURRENT_WORKER_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerDto getCurrentWorker() {
        CustomUserDetails currentUserFromContext = contextService.getCurrentUserFromContext();
        return CustomModelMapper.mapWorkerToDto(workerService.findByName(currentUserFromContext.getUsername()));
    }


    @PutMapping(path = WORKERS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public Worker update(@RequestBody WorkerDto newUser) {
        return workerService.update(newUser);
    }
}
