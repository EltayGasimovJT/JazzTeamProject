package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class UserRoleController {
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/userRoles")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    UserRolesDto addNewUserRole(@RequestBody UserRolesDto userRolesDto) throws SQLException {
        return modelMapper.map(userRolesService.save(userRolesDto), UserRolesDto.class);
    }

    @GetMapping(path = "/userRoles/{id}")
    public @ResponseBody
    UserRolesDto findById(@PathVariable Long id) throws SQLException {
        return modelMapper.map(userRolesService.findOne(id), UserRolesDto.class);
    }

    @GetMapping(path = "/userRoles")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<UserRolesDto> findAllUserRoles() throws SQLException {
        return userRolesService.findAll()
                .stream()
                .map(userRoles -> modelMapper.map(userRoles, UserRolesDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/userRoles/{id}")
    public void deleteClient(@PathVariable Long id) {
        userRolesService.delete(id);
    }

    @PutMapping("/userRoles")
    @ResponseStatus(HttpStatus.OK)
    public UserRolesDto updateUserRole(@RequestBody UserRolesDto newUserRole) throws SQLException {
        if (userRolesService.findOne(newUserRole.getId()) == null) {
            return modelMapper.map(userRolesService.save(newUserRole), UserRolesDto.class);
        } else {
            UserRolesDto clientToSave = UserRolesDto.builder()
                    .id(newUserRole.getId())
                    .role(newUserRole.getRole())
                    .build();
            return modelMapper.map(userRolesService.update(clientToSave), UserRolesDto.class);
        }
    }
}
