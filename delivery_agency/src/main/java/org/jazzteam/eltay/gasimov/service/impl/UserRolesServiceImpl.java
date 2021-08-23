package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.entity.UserRoles;
import org.jazzteam.eltay.gasimov.repository.UserRolesRepository;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserRolesServiceImpl implements UserRolesService {
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserRoles save(UserRolesDto userRolesDtoToSave) throws SQLException {
        return userRolesRepository.save(modelMapper.map(userRolesDtoToSave, UserRoles.class));
    }

    @Override
    public void delete(Long idForDelete) {
        userRolesRepository.deleteById(idForDelete);
    }

    @Override
    public List<UserRoles> findAll() throws SQLException {
        return userRolesRepository.findAll();
    }

    @Override
    public UserRoles findOne(long idForSearch) throws SQLException {
        Optional<UserRoles> foundRolesFromRepo = userRolesRepository.findById(idForSearch);
        return  foundRolesFromRepo.orElseGet(UserRoles::new);
    }

    @Override
    public UserRoles update(UserRolesDto userRolesDtoToUpdate) throws SQLException {
        return userRolesRepository.save(modelMapper.map(userRolesDtoToUpdate, UserRoles.class));
    }
}
