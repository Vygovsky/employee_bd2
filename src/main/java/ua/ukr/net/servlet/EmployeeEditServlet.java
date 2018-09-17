package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;
import ua.ukr.net.model.Employee;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Employee employee = employeeDao.findById(id);
        List<Department> departments = departmentDao.findAll();
        req.setAttribute("employee", employee);
        req.setAttribute("departments", departments);
        req.getServletContext().getRequestDispatcher("/jsp/employee_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Employee employee = new Employee();
        String id = req.getParameter("id");
        employee.setId(Long.parseLong(id));
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        employee.setBirthday(Date.valueOf(date));
        Long departId = Long.parseLong(req.getParameter("departments"));

        employeeDao.update(employee);
        employeeDao.updateDepartForEmployee(employee.getId(), departId);

        req.setAttribute("employee", employee);
        resp.sendRedirect("/employee/listEmployee");
    }
}

