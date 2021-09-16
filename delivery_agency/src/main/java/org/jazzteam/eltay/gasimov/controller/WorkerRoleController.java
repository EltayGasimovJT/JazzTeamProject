package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.service.WorkerRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class WorkerRoleController {
    @Autowired
    private WorkerRolesService workerRolesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/userRoles")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    WorkerRolesDto addNewUserRole(@RequestBody WorkerRolesDto newRoles) {
        return modelMapper.map(workerRolesService.save(newRoles), WorkerRolesDto.class);
    }

    @GetMapping(path = "/userRoles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WorkerRolesDto findById(@PathVariable Long id) {
        return modelMapper.map(workerRolesService.findOne(id), WorkerRolesDto.class);
    }

    @GetMapping(path = "/userRoles")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<WorkerRolesDto> findAllUserRoles() {
        return workerRolesService.findAll()
                .stream()
                .map(userRoles -> modelMapper.map(userRoles, WorkerRolesDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/userRoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        workerRolesService.delete(id);
    }

    @PutMapping("/userRoles")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public WorkerRolesDto updateUserRole(@RequestBody WorkerRolesDto newUserRole) {
        return modelMapper.map(workerRolesService.update(newUserRole), WorkerRolesDto.class);
    }
}