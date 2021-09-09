package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.controller.security.JwtProvider;
import org.jazzteam.eltay.gasimov.controller.security.model.AuthRequest;
import org.jazzteam.eltay.gasimov.controller.security.model.AuthResponse;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public WorkerDto registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return CustomModelMapper.mapUserToDto(workerService.saveForRegistration(registrationRequest));
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        Worker workerEntity = workerService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(workerEntity.getName());
        return new AuthResponse(token);
    }
}
