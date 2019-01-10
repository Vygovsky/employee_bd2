package ua.ukr.net;

import org.apache.commons.validator.routines.EmailValidator;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Validator {
    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    public static boolean isNameValid(final String name) {
        return name.length() >= 3 && name.matches("[\\D]+");
    }

    public boolean isEmailValid(final String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean isEmailAlreadyExisted(String email) {
        return employeeDao.findByEmail(email).getId() != 0;
    }

    public boolean isBirthdayValid(final Date birthday) {
        Date birthdayEmployee = new Date(Calendar.getInstance().getTime().getTime());
        if (birthday.after(birthdayEmployee)) {
            return false;
        }
        return true;
    }

    public static void validateDepartment(Department department, Map<String, String> errorMassages) {
        if(!isNameValid(department.getName())){
            errorMassages.put("name", "Please enter name");
        }

    }
}