package org.jazzteam.eltay.gasimov.controller.security.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RegistrationRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String role;
    @NotEmpty
    private Long workingPlaceId;
    @NotEmpty
    private String workingPlaceType;
}
