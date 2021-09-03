package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Client;

import java.util.List;

public class ClientValidator {
    private ClientValidator() {

    }

    public static void validateClient(Client clientToValidate) throws IllegalArgumentException {
        if (clientToValidate == null) {
            throw new IllegalStateException("There is no client");
        }
        if (clientToValidate.getPassportId() == null) {
            throw new IllegalArgumentException("PassportId cannot be null");
        }
        if (clientToValidate.getName() == null) {
            throw new IllegalArgumentException("Client must have name");
        }
        if (clientToValidate.getSurname() == null) {
            throw new IllegalArgumentException("Client must have surname");
        }
        if (clientToValidate.getPhoneNumber() == null) {
            throw new IllegalArgumentException(("Client must have phoneNumber"));
        }

    }

    public static void validateClientList(List<Client> clientsToValidate) throws IllegalArgumentException {
        if (clientsToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no clients on the repository");
        }
    }

    public static void validateOnSave(Client clientToValidate) throws IllegalArgumentException {
        if (clientToValidate == null) {
            throw new IllegalStateException("Client cannot be null");
        }
        validateClient(clientToValidate);
    }

    public static void validateOnFindById(Client clientToValidate, Long id) {
        if (clientToValidate == null) {
            throw new IllegalStateException("There is no client with this id " + id);
        }
    }

    public static void validateOnFindByPhoneNumber(Client clientToValidate, String phoneNumber) {
        if (clientToValidate == null) {
            throw new IllegalStateException("There is no client with this phoneNumber" + phoneNumber);
        }
    }
    public static void validateOnFindByPassport(Client clientToValidate, String passportId) {
        if (clientToValidate == null) {
            throw new IllegalStateException("There is no client with this passportId" + passportId);
        }
    }
}
