package ua.ukr.net.dao;

import ua.ukr.net.model.Employee;

import java.util.List;

public interface EmployeeDao {

    Employee createOrUpdate(Employee employee);

    Long create(Employee employee);

    Long update(Employee employee);

    void remove(Long id);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee findByEmail(String email);

    void deleteAllEmplForDepart(Long departID);

    /*void updateDepartForEmployee(Long emplId, Long departId);*/

    List<Employee> employeeByDepartmentId(Long departmentId);

    boolean isExistEmployeeInDepartByEmail(String email);

}
