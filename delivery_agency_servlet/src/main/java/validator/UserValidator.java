package validator;

import entity.User;

import java.util.List;

public class UserValidator {
    private UserValidator() {
    }

    public static void validateUser(User userToValidate) throws IllegalArgumentException {
        if (userToValidate == null) {
            throw new IllegalArgumentException("There is no user with such id!!!");
        }
        if (userToValidate.getName() == null) {
            throw new IllegalArgumentException("User must have name!!!");
        }
        if (userToValidate.getSurname() == null) {
            throw new IllegalArgumentException("User must have surname!!!");
        }
        if (userToValidate.getWorkingPlace() == null) {
            throw new IllegalArgumentException("User must have working place!!!");
        }
        if (userToValidate.getRoles().isEmpty()){
            throw new IllegalArgumentException("User must have any role!!!");
        }
    }

    public static void validateOnSave(User userToValidate) throws IllegalArgumentException {
        if (userToValidate == null) {
            throw new IllegalArgumentException("User cannot be null!!!");
        }
        validateUser(userToValidate);
    }

    public static void validateUsersList(List<User> usersToValidate) throws IllegalArgumentException {
        if (usersToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no users on the database!!!");
        }
    }
}