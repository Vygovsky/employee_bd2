package ua.ukr.net.servlet;


import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {


    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public Validate() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validateName(final String name) {
        if (name.length() < 3 || name.equals("\\d+")) {
            return true;
        }
        return false;
    }

    public boolean validateEmail(final String email) {

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateBirthday(final Date birthday) {
        Date birthdayEmployee = new Date(Calendar.getInstance().getTime().getTime());
        if (birthday.after(birthdayEmployee)) {
            return true;
        }
        return false;
    }
}