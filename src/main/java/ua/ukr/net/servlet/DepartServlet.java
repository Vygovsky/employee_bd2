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
   // private String listDepart = " ";
    //  private String insertOrEditDepart = "/jsp/create_depart.jsp";
/*для каждого эмпла из листа employeeList тебе надо вытащить из базы департ по ID, и на каждой итерации (для каждого эмпла) в мапу сложить эмпла как ключ а департ как значение
ну и передать это все на jsp
этоскорее всего то что пишу неоптимально, но пока и так сгодится, главное чтоб заработало, а там можно будет пооптимизировать
я так понял для того чтоб каждый импл был в своем департе?

просто чтоб вытащить название департамента для каждого импла

хотя все проще было бы если бы в Employee классе осталось поле Department
я могу вернуть
попробуй так сначала, потом если что вернем
давай посмортим что там у контейнера еще есть рабочее!
ничего пока работать не будет
вот здесь например надо пойти в сервлет /employee/departments в метод doGet и отредиректить на нужную jsp тсраницу
ты сразу со страницы на страницу редиректил, а теперь через сервлет
я думаю ты сделаешь*/
    private JdbcDepartmentDao jdbcDepartmentDao = new JdbcDepartmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     /*   String forward = "";
        String action = req.getParameter("action");

        if(action.equalsIgnoreCase("listDepart")){

        }*/
       Map<Department, Long> departmentCardinality = jdbcDepartmentDao.getCountOfEmployeesByDepartments();
        req.setAttribute("mapDepart", departmentCardinality.entrySet());
        req.getServletContext().getRequestDispatcher("/jsp/depart_list.jsp").forward(req, resp);
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
        resp.sendRedirect("/employee/departments");
    }
}
