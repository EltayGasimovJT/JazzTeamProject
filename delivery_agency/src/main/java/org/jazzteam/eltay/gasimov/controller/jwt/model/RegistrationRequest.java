package org.jazzteam.eltay.gasimov.controller.jwt.model;

import lombok.Data;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private WorkingPlaceType workingPlace;
}
