package ua.ukr.net.validator;

import org.apache.commons.validator.routines.EmailValidator;
import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;
import ua.ukr.net.model.Employee;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class Validator {

    private static JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();
    private static JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();


    private static boolean isNameValidDepartmentAndEmployee(String name) {
        return name.length() < 3 || !name.matches("[\\D]+"); //тут NPE падает
    }

    public static boolean isExist(String nameDepart, Map<String, String> error) {
        if (departmentDao.isDepartAlreadyExisted(nameDepart)) {
            error.put("departNameError", "Depart exist");
            return true;
        }
        return false;
    }

    public static boolean isDepartAlreadyExisted2(Department department, Map<String, String> errorMessages) {
        if (Objects.nonNull(department) && department.getId() != 0L) {
            errorMessages.put("departNameError", "Depart exist");
            return true;
        }
        return false;
    }


    private static boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private static boolean isExistEmailValid(String email) {
        return employeeDao.isExistEmployeeInDepartByEmail(email);
    }

 /*   public boolean isEmailAlreadyExisted(String email) {
        return employeeDao.findByEmail(email).getId() != 0;
    }

    public static boolean isEmailAlreadyExisted(Employee employee) {      // а вызывать будешь так: Validator.isEmailAlreadyExisted(employeeDao.findByEmail(email));
        return Objects.nonNull(employee) && employee.getName() != null;
    }*/

    private static boolean isBirthdayValid(final Date birthday) {
        Date birthdayEmployee = new Date(Calendar.getInstance().getTime().getTime());
        if (birthday.after(birthdayEmployee)) {
            return false;
        }
        return true;
    }

    public static void validateDepartment(Department department, Map<String, String> errorMessages) {
        if (!isNameValidDepartmentAndEmployee(department.getName())) {
            errorMessages.put("departNameError", "Please enter correct name (should be longer then 3 characters and contains only alpha chars)");
        }
    }

    public static void validatorEmployee(Employee employee, String email, Map<String, String> errorMessages) {
        if (!isEmailValid(employee.getEmail()) || isExistEmailValid(email)) {
            errorMessages.put("errorEmailMessage", "Email incorrect or email address already exists.");
        }
        if (isNameValidDepartmentAndEmployee(employee.getName())) {
            errorMessages.put("errorNameMessage", "Name can't be number or less two litter.");
        }
        if (!isBirthdayValid(employee.getBirthday())) {
            errorMessages.put("errorBdMessage", "Date incorrect.");
        }
    }
}