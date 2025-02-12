package service.employee;

import domain.Employee;
import repository.Dao;

import java.util.List;

public class EmployeeService {

    private Dao<Employee> employeeDao;

    public EmployeeService(Dao<Employee> employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.get(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    public boolean updateEmployee(Employee employee) {
        return employeeDao.update(employee);
    }

    public boolean delete(Employee employee) {
        return employeeDao.delete(employee);
    }

}
