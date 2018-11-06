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
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/employee/create")
public class EmployeeCreateServlet extends HttpServlet {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();
    DateTest dateTest=new DateTest();
    SimpleDateFormat date=new SimpleDateFormat();

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
        final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        Employee employee = new Employee();
        // String errorMassage = null;
        //  List<String> errorMassage = new ArrayList<>();

        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate birthday = LocalDate.parse(req.getParameter("date"));
        employee.setBirthday(java.sql.Date.valueOf(birthday));
        Long departId = Long.parseLong(req.getParameter("organizations"));
        employee.setDepartID(departId);

        pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(req, departId);

        if (employee.getName().length() < 2 || employee.getName().equals("\\d+")) {
            req.setAttribute("errorMassage", "Name can't be number or less two litter.");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
            rd.include(req, resp);

        } else if (employee.getEmail().equals(EMAIL_PATTERN)) {
            req.setAttribute("errorMassage", "Email incorrect.");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
            rd.include(req, resp);

        } else if (employee.getBirthday().equals(date.getTimeZone())) {
            req.setAttribute("errorMassage", "Date incorrect.");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
            rd.include(req, resp);
        } else {
            employeeDao.create(employee);
            req.setAttribute("departId", departId);
            req.setAttribute("employee", employee);
            resp.sendRedirect("/employee/listEmployee?departmentId=" + departId);
        }
    }
    private void pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(HttpServletRequest req, Long currentDepartId) {
        List<Department> departments = departmentDao.findAll();
        req.setAttribute("departments", departments);
        req.setAttribute("currentDepartId", currentDepartId);
    }


}


