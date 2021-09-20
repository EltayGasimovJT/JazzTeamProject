package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.jazzteam.eltay.gasimov.repository.WorkerRolesRepository;
import org.jazzteam.eltay.gasimov.service.WorkerRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerRolesServiceImpl implements WorkerRolesService {
    @Autowired
    private WorkerRolesRepository workerRolesRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkerRoles save(WorkerRolesDto workerRolesDtoToSave) {
        return workerRolesRepository.save(modelMapper.map(workerRolesDtoToSave, WorkerRoles.class));
    }

    @Override
    public void delete(Long idForDelete) {
        workerRolesRepository.deleteById(idForDelete);
    }

    @Override
    public List<WorkerRoles> findAll() {
        return workerRolesRepository.findAll();
    }

    @Override
    public WorkerRoles findOne(long idForSearch) {
        Optional<WorkerRoles> foundRolesFromRepo = workerRolesRepository.findById(idForSearch);
        return  foundRolesFromRepo.orElseGet(WorkerRoles::new);
    }

    @Override
    public WorkerRoles update(WorkerRolesDto workerRolesDtoToUpdate) {
        return workerRolesRepository.save(modelMapper.map(workerRolesDtoToUpdate, WorkerRoles.class));
    }

    @Override
    public WorkerRoles findByRole(String role) {
        return workerRolesRepository.findByRole(role);
    }
}
