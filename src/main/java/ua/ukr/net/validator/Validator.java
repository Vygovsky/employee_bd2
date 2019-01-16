package ua.ukr.net.validator;

import org.apache.commons.validator.routines.EmailValidator;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;
import ua.ukr.net.model.Employee;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Validator {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    /*public boolean isNameValid(final String name) {
        return name.length() >= 3 && name.matches("[\\D]+");
    }*/

    private static boolean isNameValidDepartment( final String name) {
        return name.length() >= 3 && name.matches("[\\D]+");
    }

    private static boolean isEmailValid(final String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean isEmailAlreadyExisted(String email) {
        return employeeDao.findByEmail(email).getId() != 0;
    }

    private static boolean isBirthdayValid(final Date birthday) {
        Date birthdayEmployee = new Date(Calendar.getInstance().getTime().getTime());
        if (birthday.after(birthdayEmployee)) {
            return false;
        }
        return true;
    }

    public static void validateDepartment(Department department, Map<String, String> errorMessages) {
        if (!isNameValidDepartment(department.getName())) {
            errorMessages.put("name", "Please enter name");
        }
    }

    public static void validatorEmployee(Employee employee, Map<String, String> errorMessages) {
        if (!isEmailValid(employee.getEmail())) {
            errorMessages.put("errorEmailMassage", "Email incorrect or email address already exists.");
        }
        if (!isNameValidDepartment(employee.getName())) {
            errorMessages.put("errorNameMassage", "Name can't be number or less two litter.");
        }
        if (!isBirthdayValid(employee.getBirthday())) {
            errorMessages.put("errorBdMassage", "Date incorrect.");
        }
    }

/*    public static void validatorEmployee(Employee employee, Map<String, String> errorMessages) {
        if (!isNameValid(employee.getName())) {
            errorMessages.put("name", "Please enter name");
        }
        if (isEmailValid(employee.getEmail())) {
            errorMessages.put("name", "Email incorrect or email address already exists");
        }
        if (!isBirthdayValid(employee.getBirthday())){
            errorMessages.put("name", "Date incorrect.");
        }
    }*/
}