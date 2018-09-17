package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteDepart")
public class DeleteDepart extends HttpServlet {
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long departId = Long.parseLong(req.getParameter("id"));
        departmentDao.remove(departId);
        resp.sendRedirect("/employee/departments");

    }
}
