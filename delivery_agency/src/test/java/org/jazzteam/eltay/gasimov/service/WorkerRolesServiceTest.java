package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.entity.Role;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WorkerRolesServiceTest {

    @Autowired
    private WorkerRolesService workerRolesService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void save() {
        WorkerRolesDto expected = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRolesDto actual = modelMapper.map(workerRolesService.save(expected), WorkerRolesDto.class);
        //expected.setId(actual.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        WorkerRolesDto firstRole = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRolesDto secondRole = WorkerRolesDto.builder()
                .role(Role.ROLE_PROCESSING_POINT_WORKER.name())
                .build();
        WorkerRoles savedFirst = workerRolesService.save(firstRole);
        WorkerRoles savedSecond = workerRolesService.save(secondRole);
        workerRolesService.delete(savedFirst.getId());

        List<WorkerRoles> actual = workerRolesService.findAll();

        Assertions.assertEquals(Collections.singletonList(savedSecond), actual);
    }

    @Test
    void findAll() {
        WorkerRolesDto firstRole = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRolesDto secondRole = WorkerRolesDto.builder()
                .role(Role.ROLE_PROCESSING_POINT_WORKER.name())
                .build();
        WorkerRoles savedFirst = workerRolesService.save(firstRole);
        WorkerRoles savedSecond = workerRolesService.save(secondRole);
        List<WorkerRoles> actual = workerRolesService.findAll();
        Assertions.assertEquals(Arrays.asList(savedFirst, savedSecond), actual);
    }

    @Test
    void findOne() {
        WorkerRolesDto expectedDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRoles expected = workerRolesService.save(expectedDto);
        WorkerRoles actual = workerRolesService.findOne(expected.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        WorkerRolesDto expectedDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRolesDto expected = modelMapper.map(workerRolesService.save(expectedDto), WorkerRolesDto.class);
        expected.setRole(Role.ROLE_PROCESSING_POINT_WORKER.name());
        WorkerRolesDto actual = modelMapper.map(workerRolesService.update(expected), WorkerRolesDto.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByRole() {
        WorkerRolesDto expectedDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        WorkerRoles expected = workerRolesService.save(expectedDto);
        WorkerRoles actual = workerRolesService.findByRole(expected.getRole());
        Assertions.assertEquals(expected, actual);
    }
}