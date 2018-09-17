package ua.ukr.net.dao;

import ua.ukr.net.model.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentDao {

    void create(Department department);

    void update(Department department);

    void remove(Long id);

    Department findID(Long id);

    List<Department> findAll();

    Department findByName(String nameDepartment);

    Map<Department, Long> getCountOfEmployeesByDepartments();
}
