package org.jazzteam.eltay.gasimov.controller.jwt.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
