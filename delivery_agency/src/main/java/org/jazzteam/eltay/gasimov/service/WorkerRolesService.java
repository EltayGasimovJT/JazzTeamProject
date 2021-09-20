package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;

import java.util.List;

public interface WorkerRolesService {
    WorkerRoles save(WorkerRolesDto workerRolesDtoToSave);

    void delete(Long idForDelete);

    List<WorkerRoles> findAll();

    WorkerRoles findOne(long idForSearch);

    WorkerRoles update(WorkerRolesDto workerRolesDtoToUpdate);

    WorkerRoles findByRole(String role);
}
