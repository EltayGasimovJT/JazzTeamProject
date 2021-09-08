package org.jazzteam.eltay.gasimov.controller.security.model;

import lombok.Data;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private AbstractBuildingDto workingPlace;
}
