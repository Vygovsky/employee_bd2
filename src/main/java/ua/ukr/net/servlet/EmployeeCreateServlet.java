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
import java.time.LocalDate;
import java.util.List;


import static java.time.LocalDate.*;


@WebServlet("/employee/create")
public class EmployeeCreateServlet extends HttpServlet {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));

        pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(req, currentDepartId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/employee_create.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Employee employee = new Employee();
        Validate validate = new Validate();

        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate birthday = parse(req.getParameter("date"));
        employee.setBirthday(java.sql.Date.valueOf(birthday));
        Long departId = Long.parseLong(req.getParameter("organizations"));
        employee.setDepartID(departId);

        pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(req, departId);
        Employee byEmail = employeeDao.findByEmail(employee.getEmail());

        if (validate.validateName(employee.getName())) {
            errorMassageByName(req, resp);

        } else if (!validate.validateEmail(employee.getEmail()) || employee.getEmail().equals(byEmail.getEmail())) {
            errorMassageByEmail(req, resp);

        } else if (validate.validateBirthday(employee.getBirthday())) {
            errorMassageByBirthday(req, resp);

        } else {
            employeeDao.create(employee);
            req.setAttribute("departId", departId);
            req.setAttribute("employee", employee);
            resp.sendRedirect("/employee/listEmployee?departmentId=" + departId);
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

    private void pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(HttpServletRequest req, Long currentDepartId) {
        List<Department> departments = departmentDao.findAll();
        req.setAttribute("departments", departments);
        req.setAttribute("currentDepartId", currentDepartId);
    }


}


