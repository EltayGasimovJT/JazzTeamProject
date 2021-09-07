package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.controller.security.JwtProvider;
import org.jazzteam.eltay.gasimov.controller.security.model.AuthRequest;
import org.jazzteam.eltay.gasimov.controller.security.model.AuthResponse;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserDto userDtoToSave = new UserDto();
        userDtoToSave.setPassword(registrationRequest.getPassword());
        userDtoToSave.setName(registrationRequest.getLogin());
        userDtoToSave.setWorkingPlace(registrationRequest.getWorkingPlace());
        userService.save(userDtoToSave);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getName());
        return new AuthResponse(token);
    }
}
