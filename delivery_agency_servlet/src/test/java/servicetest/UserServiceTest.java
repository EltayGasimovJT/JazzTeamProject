package servicetest;

import entity.AbstractBuilding;
import entity.OrderProcessingPoint;
import entity.User;
import entity.Warehouse;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import servicetest.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();

    @Test
    void addUser() throws SQLException {
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
        Assert.assertNotEquals(3, allUsers.size());
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

        Assert.assertEquals(3, allUsers.size());
    }

    @Test
    void getUser() throws SQLException {
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

        User user = userService.getUser(2);

        Assert.assertEquals(secondUser, user);
    }

    @Test
    void update() throws SQLException {
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

        Assert.assertNotEquals(workingPlace.getLocation(),
                userService
                        .getUser(user
                                .getId())
                        .getWorkingPlace()
                        .getLocation());
    }
}