package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcEmployeeDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteEmployee")
public class EmployeeDeleteServlet extends HttpServlet {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long employeeId = Long.parseLong(req.getParameter("id"));
        Long departmentId = Long.parseLong(req.getParameter("departmentId"));
        employeeDao.remove(employeeId);
        resp.sendRedirect("/employee/listEmployee?departmentId=" + departmentId);
    }
}
