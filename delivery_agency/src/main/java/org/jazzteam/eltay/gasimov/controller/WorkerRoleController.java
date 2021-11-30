package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.service.WorkerRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.util.Constants.WORKER_ROLES_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.util.Constants.WORKER_ROLES_URL;

@RestController
public class WorkerRoleController {
    @Autowired
    private WorkerRolesService workerRolesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = WORKER_ROLES_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    WorkerRolesDto save(@RequestBody WorkerRolesDto newRoles) {
        return modelMapper.map(workerRolesService.save(newRoles), WorkerRolesDto.class);
    }

    @GetMapping(path = WORKER_ROLES_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerRolesDto findById(@PathVariable Long id) {
        return modelMapper.map(workerRolesService.findOne(id), WorkerRolesDto.class);
    }

    @GetMapping(path = WORKER_ROLES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<WorkerRolesDto> findAll() {
        return workerRolesService.findAll()
                .stream()
                .map(userRoles -> modelMapper.map(userRoles, WorkerRolesDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = WORKER_ROLES_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        workerRolesService.delete(id);
    }

    @PutMapping(WORKER_ROLES_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public WorkerRolesDto update(@RequestBody WorkerRolesDto newUserRole) {
        return modelMapper.map(workerRolesService.update(newUserRole), WorkerRolesDto.class);
    }
}
