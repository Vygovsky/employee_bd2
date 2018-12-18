package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;
import ua.ukr.net.model.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/editEmployee")
public class EmployeeEditServlet extends HttpServlet {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();
    Validate validate = new Validate();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long employeeId = Long.parseLong(req.getParameter("id"));
        Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));
        Employee employee = employeeDao.findById(employeeId);
        List<Department> departments = departmentDao.findAll();

        req.setAttribute("employee", employee);
        req.setAttribute("departments", departments);
        req.setAttribute("currentDepartId", currentDepartId);
        req.getServletContext().getRequestDispatcher("/jsp/employee_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        List<Department> departments = departmentDao.findAll();
        req.setAttribute("departments", departments);

        Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));
        req.setAttribute("currentDepartId", currentDepartId);

        Long departmentId = Long.parseLong(req.getParameter("departments"));

        Employee employee = new Employee();

        Long id = Long.parseLong(req.getParameter("id"));
        employee.setId(id);
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        employee.setBirthday(Date.valueOf(date));
        employee.setDepartID(departmentId);

        boolean isNameValid = validate.isNameValid(employee.getName());
        boolean isEmailValid = validate.isEmailValid(employee.getEmail());
        boolean isBirthdayValid = validate.isBirthdayValid(employee.getBirthday());
        boolean isEmailValidAlreadyExisted = validate.isEmailAlreadyExisted(employee.getEmail());
        boolean isAllFieldsValid = isNameValid && isEmailValidAlreadyExisted && isEmailValid && isBirthdayValid;

        if (!isNameValid) {
            errorMassageByName(req, resp);
        }

        if (!isEmailValid) {
            errorMassageByEmail(req, resp);
        }

        if (!isBirthdayValid) {
            errorMassageByBirthday(req, resp);
        }

        if (isAllFieldsValid) {
            employeeDao.update(employee);
            req.setAttribute("employee", employee);
            resp.sendRedirect("/employee/listEmployee?departmentId=" + departmentId);
        } else {
            req.setAttribute("employee", employee);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_update.jsp");
            rd.include(req, resp);
        }
    }

    private void errorMassageByBirthday(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorBdMassage", "Date incorrect.");
    }

    private void errorMassageByEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorEmailMassage", "Email incorrect or email address already exists.");
    }

    private void errorMassageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorNameMassage", "Name can't be number or less two litter.");
    }
}

