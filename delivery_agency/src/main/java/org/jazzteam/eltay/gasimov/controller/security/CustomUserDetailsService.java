package org.jazzteam.eltay.gasimov.controller.security;

import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private WorkerService workerService;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Worker workerEntity = workerService.findByName(name);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(CustomModelMapper.mapUserToDto(workerEntity));
    }
}