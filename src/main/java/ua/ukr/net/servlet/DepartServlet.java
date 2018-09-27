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
                break;// это здесь нужно )))))) или нет хз я посмотрю
        }
    }

    private void deleteDepartAndEmpl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long departId = Long.parseLong(req.getParameter("id"));
        employeeDao.deleteAllEmplForDepart(departId);
        departmentDao.remove(departId);
        listOfDepartments(req, resp); // ты здесь использовал редирект, походу нельзя из сервлета сам в себя редиректить, а так просто вызывается метод который достает все данные из бд и редиректит на jsp у меня все!
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

    //todo написать метод private void updateDepartForm(HttpServletRequest req, HttpServletResponse resp) {...}, в
// которм вытащиши id департа -> вытащишь Департ из БД по id -> создашь новый департ и засетишь ему id и name и
// засетишь весь департ как параметр в req потом форварднешь на
// req.getServletContext().getRequestDispatcher("/jsp/create_depart.jsp").forward(req, resp);
// а в свиче вызовешь этот метод
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
        listOfDepartments(req, resp); //тоже самое с редиректами, а то будет бесконечно колесико крутиться на страничке
    }
}
