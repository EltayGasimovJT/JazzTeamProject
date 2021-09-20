package org.jazzteam.eltay.gasimov.controller.security.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
