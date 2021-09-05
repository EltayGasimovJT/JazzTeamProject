package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.USER_ROLES_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.USER_ROLES_URL;

@RestController
public class UserRoleController {
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = USER_ROLES_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    UserRolesDto addNewUserRole(@RequestBody UserRolesDto newRoles) {
        return modelMapper.map(userRolesService.save(newRoles), UserRolesDto.class);
    }

    @GetMapping(path = USER_ROLES_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserRolesDto findById(@PathVariable Long id) {
        return modelMapper.map(userRolesService.findOne(id), UserRolesDto.class);
    }

    @GetMapping(path = USER_ROLES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<UserRolesDto> findAllUserRoles() {
        return userRolesService.findAll()
                .stream()
                .map(userRoles -> modelMapper.map(userRoles, UserRolesDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = USER_ROLES_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        userRolesService.delete(id);
    }

    @PutMapping(path = USER_ROLES_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public UserRolesDto updateUserRole(@RequestBody UserRolesDto newUserRole) {
        return modelMapper.map(userRolesService.update(newUserRole), UserRolesDto.class);
    }
}
