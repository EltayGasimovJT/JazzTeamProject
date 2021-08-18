package validator;

import entity.Client;

import java.util.List;

public class ClientValidator {
    private ClientValidator() {

    }

    public static void validateClient(Client clientToValidate) throws IllegalArgumentException {
        if (clientToValidate == null) {
            throw new IllegalArgumentException("There is no client with such Id!!!");
        }
        if (clientToValidate.getPassportId() == null) {
            throw new IllegalArgumentException("PassportId cannot be null!!!");
        }
        if (clientToValidate.getName() == null) {
            throw new IllegalArgumentException("Client must have name!!!");
        }
        if (clientToValidate.getSurname() == null) {
            throw new IllegalArgumentException("Client must have surname!!!");
        }
        if (clientToValidate.getPhoneNumber() == null) {
            throw new IllegalArgumentException(("Client must have phoneNumber!!!"));
        }

    }

    public static void validateClientList(List<Client> clientsToValidate) throws IllegalArgumentException {
        if (clientsToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no clients on the repository!!!");
        }
    }

    public static void validateOnSave(Client clientToValidate) throws IllegalArgumentException {
        if (clientToValidate == null) {
            throw new IllegalArgumentException("Client cannot be null!!!");
        }
        validateClient(clientToValidate);
    }
}