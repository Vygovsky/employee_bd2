package ua.ukr.net.servlet;

import ua.ukr.net.dao.JdbcDepartmentDao;
import ua.ukr.net.dao.JdbcEmployeeDao;
import ua.ukr.net.model.Department;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/employee/departments")
public class DepartServlet extends HttpServlet {

    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();
    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action == null ? "list" : action) {
            case "list":
                listOfDepartments(req, resp);
                break;
            case "add":
                req.getServletContext().getRequestDispatcher("/jsp/create_depart.jsp").forward(req, resp);
                break;
            case "edit":
                updateDepartForm(req, resp);
                break;
            case "delete":
                deleteDepartAndEmpl(req, resp);
                break;
        }
    }

    private void deleteDepartAndEmpl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long departId = Long.parseLong(req.getParameter("id"));
        employeeDao.deleteAllEmplForDepart(departId);
        departmentDao.remove(departId);
        listOfDepartments(req, resp);
    }


    private void updateDepartForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long departmentId = Long.parseLong(req.getParameter("id"));
        Department department = departmentDao.findID(departmentId);
        req.setAttribute("depart", department);
        req.getServletContext().getRequestDispatcher("/jsp/create_depart.jsp").forward(req, resp);
    }

    private void listOfDepartments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Department, Long> departmentCardinality = departmentDao.getCountOfEmployeesByDepartments();
        req.setAttribute("mapDepart", departmentCardinality.entrySet());
        req.getServletContext().getRequestDispatcher("/jsp/depart_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        Department department = new Department();
        department.setName(req.getParameter("name"));
        Long departId = Long.parseLong(req.getParameter("id"));
        if (departId == null) {
            departmentDao.create(department);
        } else {
            department.setId(departId);
            departmentDao.update(department);
        }
        listOfDepartments(req, resp);
    }
}
