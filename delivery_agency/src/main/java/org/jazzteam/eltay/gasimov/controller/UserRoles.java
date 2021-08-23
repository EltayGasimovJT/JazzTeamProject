package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class UserRoles {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    User addNewUser(@RequestBody UserDto userDtoToSave) throws SQLException {
        return userService.save(userDtoToSave);
    }

    @GetMapping(path = "/users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    Iterable<User> findAllUsers() throws SQLException {
        return userService.findAll();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteCoefficient(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User replaceEmployee(@RequestBody UserDto newUser) throws SQLException {
        if (userService.findOne(newUser.getId()) == null) {
            return userService.save(newUser);
        } else {
            UserDto userToSave = UserDto.builder()
                    .id(newUser.getId())
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .roles(newUser.getRoles())
                    .workingPlace(newUser.getWorkingPlace())
                    .build();
            return userService.update(userToSave);
        }
    }
}
