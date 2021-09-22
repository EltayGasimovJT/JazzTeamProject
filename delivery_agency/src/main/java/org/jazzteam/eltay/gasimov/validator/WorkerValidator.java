package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Worker;

import java.util.List;

public class WorkerValidator {
    private WorkerValidator() {
    }

    public static void validateUser(Worker workerToValidate) throws IllegalArgumentException {
        if (workerToValidate == null) {
            throw new IllegalArgumentException("There is no worker with such id");
        }
        if (workerToValidate.getName() == null) {
            throw new IllegalArgumentException("Worker must have name");
        }
        if (workerToValidate.getSurname() == null) {
            throw new IllegalArgumentException("Worker must have surname");
        }
        if (workerToValidate.getWorkingPlace() == null) {
            throw new IllegalArgumentException("Worker must have working place");
        }
        if (workerToValidate.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Worker must have any role");
        }
    }

    public static void validateOnSave(Worker workerToValidate) throws IllegalArgumentException {
        if (workerToValidate == null) {
            throw new IllegalArgumentException("Worker cannot be null");
        }
        validateUser(workerToValidate);
    }

    public static void validateUsersList(List<Worker> usersToValidate) throws IllegalArgumentException {
        if (usersToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no users on the database");
        }
    }
}
