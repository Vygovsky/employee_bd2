package ua.ukr.net.servlet;

import org.apache.commons.validator.routines.EmailValidator;
import ua.ukr.net.dao.JdbcEmployeeDao;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Validate {
    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    public boolean isNameValid(final String name) throws ServletException, IOException {
        return name.length() >= 3 && !name.equals("\\d+");
    }

    public boolean isEmailValid(final String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean isEmailAlreadyExisted(String email) {
        return employeeDao.findByEmail(email) != null;
    }


    public boolean isBirthdayValid(final Date birthday) {
        Date birthdayEmployee = new Date(Calendar.getInstance().getTime().getTime());
        if (birthday.after(birthdayEmployee)) {
            return false;
        }
        return true;
    }
}