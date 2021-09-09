package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;


public interface ContextService {
    CustomUserDetails getCurrentUserFromContext();
}
