package service;

import entity.User;
import entity.Warehouse;
import org.junit.jupiter.api.Test;
import service.impl.UserServiceImpl;

import java.util.Arrays;

class UserServiceTest {

    @Test
    void addUser() {
        User user = new User();
        UserService userService = new UserServiceImpl();
        user.setId(1);
        user.setName("Igor");
        user.setSurname("Igor");
        user.setRoles(Arrays.asList("Admin, User"));
        User user1 = User
                .builder()
                .id(1)
                .name("Igor")
                .surname("Igor")
                .workingPlace(new Warehouse())
                .roles(Arrays.asList("User", "Client"))
                .build();

        userService.addUser(user);
        userService.addUser(user1);


        userService.findAllUsers();
    }

    @Test
    void deleteUser() {
    }

    @Test
    void showUsers() {
    }

    @Test
    void getUser() {
    }

    @Test
    void update() {
    }
}