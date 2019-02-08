package ua.ukr.net.dao;

import ua.ukr.net.model.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDepartmentDao extends AbstractJdbcDao implements DepartmentDao {
    private final static String BD_FIND_ALL_DEPART = "SELECT*FROM department";
    private final String FIND_BY_NAME_DEPART = "SELECT * FROM department WHERE name=?";
    private final String FIND_BY_ID_DEPART = "SELECT * FROM department WHERE id=?";
    private final String UPDATE_DEPART = "UPDATE department SET name=? WHERE id=?";
    private final String DELETE_DEPART = "DELETE FROM department WHERE id=?";
    private final String INSERT_DEPART = "INSERT INTO department (name) VALUES(?)";
    private final String COUNT_EMPL_IN_DEPART = "SELECT depart_id,name, COUNT(e_id) empl_count\n" +
            "FROM (SELECT  e.id e_id, d.id depart_id, d.name FROM department d LEFT JOIN employee e on d.id = e.department_id) as n\n" +
            "GROUP BY depart_id";
    private final String IS_EXIST = "SELECT name from DEPARTMENT";

    @Override
    public Department createOrUpdate(Department department) {
        Long id;
        if (department.getId() == null) {
            id = create(department);
        } else {
            id = update(department);
        }
        return findID(id);
    }

    @Override
    public Long create(Department department) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(INSERT_DEPART, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, department.getName());
            preparedStatement.execute();
            return parseResponse(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long parseResponse(PreparedStatement stmtInsert) throws SQLException {
        ResultSet rs = stmtInsert.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }
        return null;
    }

    @Override
    public Long update(Department department) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(UPDATE_DEPART);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setLong(2, department.getId());
            preparedStatement.executeUpdate();
            return department.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(DELETE_DEPART);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> departmentList = new ArrayList<>();
        try {
            Statement statement = createConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(BD_FIND_ALL_DEPART);
            while (resultSet.next()) {
                Department department = new Department(
                        resultSet.getLong("ID"),
                        resultSet.getString("NAME"));
                departmentList.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    @Override
    public Department findByName(String nameDepartment) {
        Department department = new Department(0L, null);
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(FIND_BY_NAME_DEPART);
            preparedStatement.setString(1, nameDepartment);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department.setId(resultSet.getLong("ID"));
                department.setName(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Department findID(Long id) {
        Department department = new Department();
        try {
            PreparedStatement preparedStatement = createConnection().prepareStatement(FIND_BY_ID_DEPART);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department.setId(resultSet.getLong("ID"));
                department.setName(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Map<Department, Long> getCountOfEmployeesByDepartments() {
        Map<Department, Long> map = new HashMap<>();
        Department department;
        try {
            Statement statement = createConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_EMPL_IN_DEPART);
            while (resultSet.next()) {
                department = new Department(resultSet.getLong("DEPART_ID"), resultSet.getString("NAME"));
                map.put(department, resultSet.getLong("EMPL_COUNT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String isDepartAlreadyExisted(String nameS) {
        String name = "";
        try {
            Statement statement = createConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(IS_EXIST);
            while (resultSet.next()) {
                Department department = new Department();
                department.setName(resultSet.getString("NAME"));
                if (department.getName().equalsIgnoreCase(nameS)) {
                    name=department.getName();
                    return name;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}



