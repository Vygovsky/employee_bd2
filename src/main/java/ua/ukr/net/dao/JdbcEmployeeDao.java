package ua.ukr.net.dao;

import ua.ukr.net.model.Department;
import ua.ukr.net.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmployeeDao extends AbstractJdbcDao implements EmployeeDao {
    private final String FIND_ALL_EMPL = "SELECT EMPLOYEE.ID as ID, FIRST_NAME, EMAIL, BIRTHDAY, NAME FROM EMPLOYEE\n" +
            "  LEFT JOIN EMPLOYEE_DEPARTMENT ON EMPLOYEE.ID = EMPLOYEE_DEPARTMENT.EMPLOYEE_ID\n" +
            "  LEFT JOIN DEPARTMENT ON EMPLOYEE_DEPARTMENT.DEPARTMENT_ID = DEPARTMENT.ID;";
    private final String FIND_BY_ID_EMPL = "SELECT * FROM employee WHERE id=?";
    private final String FIND_BY_EMAIL_EMPL = "SELECT * FROM employee WHERE email=?";
    private final String UPDATE_EMPLOYEE = "UPDATE employee SET first_name=?, email=?, birthday=? WHERE id=?";
    private final String DELETE = "DELETE FROM employee WHERE id=?";
    private final String INSERT_EMPL = "INSERT INTO employee (first_name, email, birthday) VALUES(?,?,?)";
    private final String UPDATE_BY_DEPART_FOR_IMPL = "UPDATE employee_department SET department_id=? WHERE employee_id=?";


    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(UPDATE_EMPLOYEE);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setDate(3, employee.getBirthday());
            preparedStatement.setLong(4, employee.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void create(Employee employee) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(INSERT_EMPL);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setDate(3, employee.getBirthday());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Long id) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> listEmployee = new ArrayList<>();
        try {
            Statement statement = createConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_EMPL);

            while (resultSet.next()) {
                Employee employee = new Employee();
                Department department = new Department();
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("FIRST_NAME"));
                employee.setEmail(resultSet.getString("EMAIL"));
                employee.setBirthday(resultSet.getDate("BIRTHDAY"));
                department.setName(resultSet.getString("NAME"));
                employee.setDepartment(department);
                listEmployee.add(employee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployee;
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(FIND_BY_ID_EMPL);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("FIRST_NAME"));
                employee.setEmail(resultSet.getString("EMAIL"));
                employee.setBirthday(resultSet.getDate("BIRTHDAY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employeeMail = new Employee();
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(FIND_BY_EMAIL_EMPL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeMail.setId(resultSet.getLong("ID"));
                employeeMail.setName(resultSet.getString("FIRST_NAME"));
                employeeMail.setEmail(resultSet.getString("EMAIL"));
                employeeMail.setBirthday(resultSet.getDate("BIRTHDAY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeMail;
    }


    @Override
    public void updateDepartForEmployee(Long emplId, Long departId) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(UPDATE_BY_DEPART_FOR_IMPL);
            preparedStatement.setLong(1, departId);
            preparedStatement.setLong(2, emplId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

