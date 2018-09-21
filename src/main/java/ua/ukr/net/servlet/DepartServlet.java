package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.model.Department;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/employee/departments")
public class DepartServlet extends HttpServlet {
    private String listDepart = "/jsp/depart_list.jsp";
    private String insertOrEditDepart = "/jsp/create_depart.jsp";

    private JdbcDepartmentDao jdbcDepartmentDao = new JdbcDepartmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if(action.equalsIgnoreCase("listDepart")){

        }
        Map<Department, Long> departmentCardinality = jdbcDepartmentDao.getCountOfEmployeesByDepartments();
        req.setAttribute("mapDepart", departmentCardinality.entrySet());
        req.getServletContext().getRequestDispatcher(listDepart).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Department department = new Department();
        department.setName(req.getParameter("name"));
        String departId = req.getParameter("id");
        if (departId == null || departId.isEmpty()) {
            jdbcDepartmentDao.create(department);
        } else {
            department.setId(Long.parseLong(departId));
            jdbcDepartmentDao.update(department);
        }

        RequestDispatcher viewer = req.getServletContext().getRequestDispatcher(insertOrEditDepart);
        req.setAttribute("depart", jdbcDepartmentDao.findAll());
        viewer.forward(req, resp);


    }
}
