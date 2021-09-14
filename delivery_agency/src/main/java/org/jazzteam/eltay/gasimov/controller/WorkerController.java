package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.ContextService;
import org.jazzteam.eltay.gasimov.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Log
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ContextService contextService;

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Worker addNewUser(@RequestBody WorkerDto workerDtoToSave) {
        return workerService.save(workerDtoToSave);
    }

    @PostMapping(path = "/users/changeWorkingPlace/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody
    Worker changeWorkingPlace(@PathVariable Long id, @RequestBody AbstractBuildingDto newWorkingPlace) {
        return workerService.changeWorkingPlace(id, newWorkingPlace);
    }

    @GetMapping(path = "/users")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Worker> findAllUsers() {
        return workerService.findAll();
    }

    @DeleteMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        workerService.delete(id);
    }

    @GetMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerDto findById(@PathVariable Long id) {
        return CustomModelMapper.mapUserToDto(workerService.findOne(id));
    }

    @GetMapping(path = "/users/findRoles")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<String> findRoles() {
        CustomUserDetails foundWorkerFromContext = contextService.getCurrentUserFromContext();
        final Set<WorkerRoles> foundRoles = workerService.findWorkerRoles(foundWorkerFromContext.getUsername());
        return foundRoles.stream()
                .map(WorkerRoles::getRole)
                .collect(Collectors.toSet());
    }

    @GetMapping(path = "/users/getStatesByRole")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<String> getStatesByRole() {
        CustomUserDetails currentUserFromContext = contextService.getCurrentUserFromContext();
        Worker foundByName = workerService.findByName(currentUserFromContext.getUsername());
        return workerService.findStatesByRole(foundByName);
    }

    @GetMapping(path = "/users/getCurrentWorker")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerDto getCurrentWorker() {
        CustomUserDetails currentUserFromContext = contextService.getCurrentUserFromContext();
        return CustomModelMapper.mapUserToDto(workerService.findByName(currentUserFromContext.getUsername()));
    }


    @PutMapping("/users")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public Worker updateUser(@RequestBody WorkerDto newUser) {
        return workerService.update(newUser);
    }
}
