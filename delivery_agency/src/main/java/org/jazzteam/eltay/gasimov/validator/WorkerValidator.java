package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Worker;

import java.util.List;

public class WorkerValidator {
    private WorkerValidator() {
    }

    public static void validateUser(Worker workerToValidate) throws IllegalArgumentException {
        if (workerToValidate == null) {
            throw new IllegalArgumentException("В базе данных нет такого сотрудника");
        }
        if (workerToValidate.getName() == null) {
            throw new IllegalArgumentException("Значение имени сотрудника должно быть заполнено");
        }
        if (workerToValidate.getSurname() == null) {
            throw new IllegalArgumentException("Значение фамилии сотрудника должно быть заполнено");
        }
        if (workerToValidate.getWorkingPlace() == null) {
            throw new IllegalArgumentException("Значение места работы сотрудника должно быть заполнено");
        }
        if (workerToValidate.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Значение роли у сотрудника должно быть заполнено");
        }
    }

    public static void validateOnSave(Worker workerToValidate) throws IllegalArgumentException {
        if (workerToValidate == null) {
            throw new IllegalArgumentException("Введенные данные о сотруднике не верны");
        }
        validateUser(workerToValidate);
    }

    public static void validateUsersList(List<Worker> usersToValidate) throws IllegalArgumentException {
        if (usersToValidate.isEmpty()) {
            throw new IllegalArgumentException("В базе данных нет сотрудников");
        }
    }
}
