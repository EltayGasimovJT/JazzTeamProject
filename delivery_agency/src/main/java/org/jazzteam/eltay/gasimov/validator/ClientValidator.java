package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.Client;

import java.util.List;

public class ClientValidator {
    private ClientValidator() {

    }

    public static void validateClient(Client clientToValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (clientToValidate == null) {
            throw new ObjectNotFoundException("В базе данных нет такого клиента");
        }
        if (clientToValidate.getPassportId() == null) {
            throw new IllegalArgumentException("Значение паспорта должно быть заполнено");
        }
        if (clientToValidate.getName() == null) {
            throw new IllegalArgumentException("Значение имени клиента должно быть заполнено");
        }
        if (clientToValidate.getSurname() == null) {
            throw new IllegalArgumentException("Значение фамилии клиента должно быть заполнено");
        }
        if (clientToValidate.getPhoneNumber() == null) {
            throw new IllegalArgumentException(("Значение номера телефона клиента должно быть заполнено"));
        }

    }

    public static void validateClientList(List<Client> clientsToValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (clientsToValidate.isEmpty()) {
            throw new ObjectNotFoundException("В базе данных нет информации о клиентах");
        }
    }

    public static void validateOnSave(Client clientToValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (clientToValidate == null) {
            throw new IllegalStateException("Введенные данные клиента не верны");
        }
        validateClient(clientToValidate);
    }

    public static void validateOnFindById(Client clientToValidate, Long id) throws ObjectNotFoundException {
        if (clientToValidate == null) {
            throw new ObjectNotFoundException("Не существует клиента с таким id: " + id);
        }
    }

    public static void validateOnFindByPhoneNumber(Client clientToValidate, String phoneNumber) throws ObjectNotFoundException {
        if (clientToValidate == null) {
            throw new ObjectNotFoundException("Не существует клиента с таким номером телефона: " + phoneNumber);
        }
    }
    public static void validateOnFindByPassport(Client clientToValidate, String passportId) throws ObjectNotFoundException {
        if (clientToValidate == null) {
            throw new ObjectNotFoundException("Не существует клиента с таким номером паспорта: " + passportId);
        }
    }
}
