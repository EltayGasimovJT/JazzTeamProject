package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = USERS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    User addNewUser(@RequestBody UserDto userDtoToSave) {
        return userService.save(userDtoToSave);
    }

    @PostMapping(path = USERS_CHANGE_WORKING_PLACE_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody
    User changeWorkingPlace(@PathVariable Long id, @RequestBody AbstractBuildingDto newWorkingPlace) {
        return userService.changeWorkingPlace(id, newWorkingPlace);
    }

    @GetMapping(path = USERS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping(path = USERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping(path = USERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserDto findById(@PathVariable Long id) {
        return modelMapper.map(userService.findOne(id), UserDto.class);
    }

    @PutMapping(path = USERS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public User updateUser(@RequestBody UserDto newUser) {
        return userService.update(newUser);
    }
}
