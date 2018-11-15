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
    private static final String EMPL_BY_DEPART_ID = "SELECT employee.id,employee.first_name, employee.email, employee.birthday,\n" +
            "d.name FROM employee LEFT JOIN department d on employee.department_id = d.id where d.id=?;";
    private final String FIND_ALL_EMPL = "SELECT * FROM employee";
    private final String FIND_BY_ID_EMPL = "SELECT * FROM employee WHERE id=?";
    private final String FIND_BY_EMAIL_EMPL = "SELECT * FROM employee WHERE email=?";
    private final String UPDATE_EMPLOYEE = "UPDATE employee SET first_name=?, email=?, birthday=?, department_id=? WHERE id=?";
    private final String DELETE = "DELETE FROM employee WHERE id=?";
    private final String INSERT_EMPL = "INSERT INTO employee (first_name, email, birthday, department_id) VALUES(?,?,?,?)";
    private final String DELETE_EMPL_DEPART_ID = "DELETE FROM employee WHERE department_id = ?";

    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(UPDATE_EMPLOYEE);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setDate(3, employee.getBirthday());
            preparedStatement.setLong(4, employee.getDepartID());
            preparedStatement.setLong(5, employee.getId());
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
            preparedStatement.setLong(4, employee.getDepartID());
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
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("FIRST_NAME"));
                employee.setEmail(resultSet.getString("EMAIL"));
                employee.setBirthday(resultSet.getDate("BIRTHDAY"));
                employee.setDepartID(resultSet.getLong("DEPARTMENT_ID"));
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
                employee.setDepartID(resultSet.getLong("DEPARTMENT_ID"));
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
            repeatSQLRequests(employeeMail, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeMail;
    }

    private void repeatSQLRequests(Employee employeeMail, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employeeMail.setId(resultSet.getLong("ID"));
            employeeMail.setName(resultSet.getString("FIRST_NAME"));
            employeeMail.setEmail(resultSet.getString("EMAIL"));
            employeeMail.setBirthday(resultSet.getDate("BIRTHDAY"));
            employeeMail.setDepartID(resultSet.getLong("DEPARTMENT_ID"));
        }
    }

    @Override
    public void deleteAllEmplForDepart(Long departID) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(DELETE_EMPL_DEPART_ID);
            preparedStatement.setLong(1, departID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> employeeByDepartmentId(Long departmentId) {
        List<Employee> listEmployee = new ArrayList<>();
        Department department = new Department();
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(EMPL_BY_DEPART_ID);
            preparedStatement.setLong(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("FIRST_NAME"));
                employee.setEmail(resultSet.getString("EMAIL"));
                employee.setBirthday(resultSet.getDate("BIRTHDAY"));
                department.setId(resultSet.getLong("ID"));
                department.setName(resultSet.getString("NAME"));
                employee.setDepartment(department);
                listEmployee.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployee;
    }
}



