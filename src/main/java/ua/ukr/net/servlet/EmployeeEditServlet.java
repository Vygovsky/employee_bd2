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

        Employee employee = new Employee();
        List<Department> departments = departmentDao.findAll();
        req.setAttribute("departments", departments);
        Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));
        req.setAttribute("currentDepartId", currentDepartId);
        String id = req.getParameter("id");
        Long departmentId = Long.parseLong(req.getParameter("departments"));
        employee.setDepartID(departmentId);
        employee.setId(Long.parseLong(id));
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        employee.setBirthday(Date.valueOf(date));

        Employee byEmail = employeeDao.findByEmail(employee.getEmail());

        if (validate.validateName(employee.getName())) {
            errorMassageByName(req, resp);

        } else if (!validate.validateEmail(employee.getEmail()) || employee.getEmail().equals(byEmail.getEmail())) {
            errorMassageByEmail(req, resp);

        } else if (validate.validateBirthday(employee.getBirthday())) {
            errorMassageByBirthday(req, resp);

        } else {
            employeeDao.update(employee);
            req.setAttribute("employee", employee);
            resp.sendRedirect("/employee/listEmployee?departmentId=" + departmentId);
        }
    }

    private void errorMassageByBirthday(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMassage", "Date incorrect.");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
        rd.include(req, resp);
    }

    private void errorMassageByEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMassage", "Email incorrect or email address already exists.");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
        rd.include(req, resp);
    }

    private void errorMassageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMassage", "Name can't be number or less two litter.");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
        rd.include(req, resp);
    }
}

