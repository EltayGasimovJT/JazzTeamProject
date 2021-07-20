package repository.impl;

import entity.Employee;
import repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();


    @Override
    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    @Override
    public void delete(Employee employee) {
        employees.remove(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee findOne(int num) {
        return null;
    }
}
