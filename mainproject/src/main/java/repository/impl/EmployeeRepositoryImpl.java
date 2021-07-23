package repository.impl;

import entity.User;
import repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final List<User> employees = new ArrayList<>();

    @Override
    public User save(User employee) {
        employees.add(employee);
        return employee;
    }

    @Override
    public void delete(User employee) {
        employees.remove(employee);
    }

    @Override
    public List<User> findAll() {
        return employees;
    }

    @Override
    public User findOne(String string) {
        return employees.stream()
                .filter(client -> client.getEmployeeRole().toString().equals(string))
                .findFirst()
                .orElse(null);
    }
}
