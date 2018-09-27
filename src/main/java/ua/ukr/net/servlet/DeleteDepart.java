/*
package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.dao.JdbcEmployeeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteDepart")

public class DeleteDepart extends HttpServlet {
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();
    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long employeeId = Long.parseLong(req.getParameter("department_id"));
        Long departId = Long.parseLong(req.getParameter("id"));
        employeeDao.deleteAllEmplForDepart(employeeId);
        departmentDao.remove(departId);
        resp.sendRedirect("/employee/departments");

    }
}
*/
