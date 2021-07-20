package entity;

import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import java.time.YearMonth;

public class Employee {
    private long employeeId;
    private EmployeeRole employeeRole;
    private YearMonth workTime;
    private EmployeeService employeeService;

    public Employee() {
        employeeService = new EmployeeServiceImpl();
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public YearMonth getWorkTime() {
        return workTime;
    }

    public void setWorkTime(YearMonth workTime) {
        this.workTime = workTime;
    }
}
