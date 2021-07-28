package service;

import entity.User;
import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();

    @Test
    void addUser() {
        User user = User
                .builder()
                .id(1)
                .name("Igor")
                .surname("Igor")
                .workingPlace(new Warehouse())
                .roles(Arrays.asList("User", "Client"))
                .build();

        User addUser = userService.addUser(user);

        Assert.assertEquals(1, addUser.getId());
    }

    @Test
    void deleteUser() {
        User user1 = User
                .builder()
                .id(1)
                .build();
        User user2 = User
                .builder()
                .id(2)
                .build();
        User user3 = User
                .builder()
                .id(3)
                .build();

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        userService.deleteUser(user1);

        List<User> allUsers = userService.findAllUsers();
        Assert.assertNotEquals(3, allUsers.size());
    }

    @Test
    void findAllUsers() {
        User user1 = User
                .builder()
                .id(1)
                .build();
        User user2 = User
                .builder()
                .id(2)
                .build();
        User user3 = User
                .builder()
                .id(3)
                .build();

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        List<User> allUsers = userService.findAllUsers();

        Assert.assertEquals(3, allUsers.size());
    }

    @Test
    void getUser() {
        User user1 = User
                .builder()
                .id(1)
                .build();
        User user2 = User
                .builder()
                .id(2)
                .build();
        User user3 = User
                .builder()
                .id(3)
                .build();
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        User user = userService.getUser(2);

        Assert.assertEquals(2, user.getId());
    }

    @Test
    void update() {
        User user = User
                .builder()
                .id(1)
                .name("Vlad")
                .build();
        userService.addUser(user);

        User newUser = User
                .builder()
                .id(1)
                .name("Victor")
                .build();

        User update = userService.update(newUser);

        Assert.assertEquals("Victor", update.getName());
    }
}