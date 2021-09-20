package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;
import org.jazzteam.eltay.gasimov.service.ContextService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("contextService")
public class ContextServiceImpl implements ContextService {
    @Override
    public CustomUserDetails getCurrentUserFromContext() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
