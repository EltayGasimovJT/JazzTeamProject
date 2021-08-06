package service;

import entity.AbstractBuilding;
import entity.OrderProcessingPoint;
import entity.User;
import entity.Warehouse;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();

    @Test
    void addUser() throws SQLException {
        User expected = User
                .builder()
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .workingPlace(new Warehouse())
                .roles(Arrays.asList("User", "Client"))
                .build();

        User actual = userService.addUser(expected);

        Assert.assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        User firstUser = User
                .builder()
                .id(1L)
                .build();
        User secondUser = User
                .builder()
                .id(2L)
                .build();
        User thirdUser = User
                .builder()
                .id(3L)
                .build();

        userService.addUser(firstUser);
        userService.addUser(secondUser);
        userService.addUser(thirdUser);

        userService.deleteUser(firstUser);

        List<User> allUsers = userService.findAllUsers();
        int unexpected = 3;

        int actual = allUsers.size();

        Assert.assertNotEquals(unexpected, actual);
    }

    @Test
    void findAllUsers() throws SQLException {
        User firstUser = User.builder().build();
        User secondUser = User.builder().build();
        User thirdUser = User.builder().build();

        userService.addUser(firstUser);
        userService.addUser(secondUser);
        userService.addUser(thirdUser);

        List<User> allUsers = userService.findAllUsers();

        int expected = 3;

        int actual = allUsers.size();

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getUser() throws SQLException {
        User firstUser = User
                .builder()
                .id(1L)
                .build();
        User expected = User
                .builder()
                .id(2L)
                .build();
        User thirdUser = User
                .builder()
                .id(3L)
                .build();
        userService.addUser(firstUser);
        userService.addUser(expected);
        userService.addUser(thirdUser);

        User actual = userService.getUser(2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        User user = User
                .builder()
                .id(1L)
                .name("Vlad")
                .build();
        userService.addUser(user);

        String expected = "Victor";
        User newUser = User
                .builder()
                .id(1L)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    void changeWorkingPlace() throws SQLException {
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

        String expected = workingPlace.getLocation();

        String actual = userService
                .getUser(user
                        .getId())
                .getWorkingPlace()
                .getLocation();
        Assert.assertNotEquals(expected,
                actual);
    }
}