package ua.ukr.net.servlet;

import org.h2.util.StringUtils;
import ua.ukr.net.validator.Validator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static java.time.LocalDate.*;

@WebServlet("/employee/create")
public class EmployeeCreateServlet extends HttpServlet {

    private JdbcEmployeeDao employeeDao = new JdbcEmployeeDao();
    private JdbcDepartmentDao departmentDao = new JdbcDepartmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        String action = req.getParameter("action");
        switch (action == null ? "list" : action) {
            case "list":
                listOfEmployeesInDepartment(req, resp);
                break;
            case "add":
                Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));
                addEmployee(req, resp, currentDepartId);
                break;
            case "edit":
                updateEmployeeForm(req, resp);
                break;
            case "delete":
                deleteEmployee(req, resp);
                break;
        }
    }

    private void addEmployee(HttpServletRequest req, HttpServletResponse resp, Long currentDepartId) throws ServletException, IOException {
        pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(req, currentDepartId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/employee_create.jsp");
        dispatcher.forward(req, resp);
    }

    private void pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(HttpServletRequest req, Long currentDepartId) {
        List<Department> departments = departmentDao.findAll();
        req.setAttribute("departments", departments);
        req.setAttribute("currentDepartId", currentDepartId);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long employeeId = Long.parseLong(req.getParameter("id"));
        employeeDao.remove(employeeId);
        listOfEmployeesInDepartment(req, resp);
    }

    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long employeeId = Long.parseLong(req.getParameter("id"));
        Long currentDepartId = Long.parseLong(req.getParameter("currentDepartId"));
        Employee employee = employeeDao.findById(employeeId);
        List<Department> departments = departmentDao.findAll();

        req.setAttribute("employee", employee);
        req.setAttribute("departments", departments);
        req.setAttribute("currentDepartId", currentDepartId);
        req.getServletContext().getRequestDispatcher("/jsp/employee_update.jsp").forward(req, resp);
    }

    private void listOfEmployeesInDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long departmentId = Long.parseLong(req.getParameter("departmentId"));
        List<Employee> employeeList = employeeDao.employeeByDepartmentId(departmentId);
        req.setAttribute("employeeList", employeeList);
        req.setAttribute("departmentId", departmentId);
        req.getServletContext().getRequestDispatcher("/jsp/employee_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        String employeeId = req.getParameter("id");


        Map<String, String> errorMassages = new HashMap<>();

        Employee employee;
        Validator validator = new Validator();

        if (StringUtils.isNullOrEmpty(employeeId)) {
            employee = new Employee();
        } else {
            employee = employeeDao.findById(Long.parseLong(employeeId));
        }
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        LocalDate birthday = parse(req.getParameter("date"));
        employee.setBirthday(java.sql.Date.valueOf(birthday));
        Long departId = Long.parseLong(req.getParameter("organizations"));
        employee.setDepartID(departId);
    //    pushListOfDepartmentsWithCurrentDepartmentIdAsRequestParameters(req, departId);
        if (errorMassages.isEmpty()) {
            employee = employeeDao.createOrUpdate(employee);
            req.setAttribute("departId", departId);
            req.setAttribute("employee", employee);
            resp.sendRedirect("/employee/create?action=list&departmentId=" + departId);
        } else {
            req.setAttribute("employee", employee);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
            rd.include(req, resp);
        }
    }


   /* boolean isNameValid = validator.isNameValid(employee.getName());
    boolean isEmailValid = validator.isEmailValid(employee.getEmail());
    boolean isBirthdayValid = validator.isBirthdayValid(employee.getBirthday());
    boolean isEmailValidAlreadyExisted = validator.isEmailAlreadyExisted(employee.getEmail());
    boolean isAllFieldsValid = isNameValid && !isEmailValidAlreadyExisted && isEmailValid && isBirthdayValid;

        if(!isNameValid)

    {
        errorMassageByName(req, resp);
    }

        if(!isEmailValid ||isEmailValidAlreadyExisted)

    {
        errorMassageByEmail(req, resp);
    }

        if(!isBirthdayValid)

    {
        errorMassageByBirthday(req, resp);
    }

        if(isAllFieldsValid)

    {
        employeeDao.create(employee);
        req.setAttribute("departId", departId);
        req.setAttribute("employee", employee);
        resp.sendRedirect("/employee/create?action=list&departmentId=" + departId);
    } else

    {
        req.setAttribute("employee", employee);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/employee_create.jsp");
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

*/
}


