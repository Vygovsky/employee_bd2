package ua.ukr.net.servlet;

import org.apache.commons.validator.routines.EmailValidator;
import ua.ukr.net.dao.JdbcEmployeeDao;

import java.util.Calendar;
import java.util.Date;

public class Validate {
    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    public boolean isNameValid(final String name) {
        return name.length() >= 3 && !name.equals("\\d+");
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
}