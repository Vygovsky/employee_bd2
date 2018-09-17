package ua.ukr.net.dao;

import ua.ukr.net.model.Employee;

import java.util.List;

public interface EmployeeDao {

    void create(Employee employee);

    void update(Employee employee);

    void remove(Long id);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee findByEmail(String email);

   void updateDepartForEmployee(Long emplId, Long departId);
}
