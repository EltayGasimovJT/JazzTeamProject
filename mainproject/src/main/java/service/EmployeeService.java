package service;

import entity.Employee;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    List<Employee> showEmployees();

    Employee getEmployeeById(int employeeId);
}
