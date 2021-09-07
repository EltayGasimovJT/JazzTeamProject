package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
public class WorkerController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    User addNewUser(@RequestBody UserDto userDtoToSave) {
        return userService.save(userDtoToSave);
    }

    @PostMapping(path = "/users/changeWorkingPlace/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody
    User changeWorkingPlace(@PathVariable Long id, @RequestBody AbstractBuildingDto newWorkingPlace) {
        return userService.changeWorkingPlace(id, newWorkingPlace);
    }

    @GetMapping(path = "/users")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserDto findById(@PathVariable Long id) {
        return CustomModelMapper.mapUserToDto(userService.findOne(id));
    }

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public User updateUser(@RequestBody UserDto newUser) {
        return userService.update(newUser);
    }
}
