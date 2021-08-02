package service;

import entity.AbstractBuilding;
import entity.OrderProcessingPoint;
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
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .workingPlace(new Warehouse())
                .roles(Arrays.asList("User", "Client"))
                .build();

        User addUser = userService.addUser(user);

        Assert.assertEquals(user, addUser);
    }

    @Test
    void deleteUser() {
        User user1 = User
                .builder()
                .id(1L)
                .build();
        User user2 = User
                .builder()
                .id(2L)
                .build();
        User user3 = User
                .builder()
                .id(3L)
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
        User user1 = User.builder().build();
        User user2 = User.builder().build();
        User user3 = User.builder().build();

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
                .id(1L)
                .build();
        User user2 = User
                .builder()
                .id(2L)
                .build();
        User user3 = User
                .builder()
                .id(3L)
                .build();
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        User user = userService.getUser(2);

        Assert.assertEquals(user2, user);
    }

    @Test
    void update() {
        User user = User
                .builder()
                .id(1L)
                .name("Vlad")
                .build();
        userService.addUser(user);

        User newUser = User
                .builder()
                .id(1L)
                .name("Victor")
                .build();

        User update = userService.update(newUser);

        Assert.assertEquals("Victor", update.getName());
    }

    @Test
    void changeWorkingPlace() {
        AbstractBuilding workingPlace = new OrderProcessingPoint();
        workingPlace.setLocation("Minsk");
        User user = User
                .builder()
                .id(1L)
                .workingPlace(workingPlace)
                .name("Vlad")
                .build();

        userService.addUser(user);

        AbstractBuilding newWorkingPlace = new OrderProcessingPoint();
        newWorkingPlace.setLocation("Vitebsk");

        userService.changeWorkingPlace(userService.getUser(user.getId()), newWorkingPlace);

        Assert.assertNotEquals(workingPlace.getLocation(),
                userService
                        .getUser(user
                                .getId())
                        .getWorkingPlace()
                        .getLocation());
    }
}