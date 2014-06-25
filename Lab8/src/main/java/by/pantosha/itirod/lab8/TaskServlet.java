package by.pantosha.itirod.lab8;

import by.pantosha.itirod.lab5.entity.Subtask;
import by.pantosha.itirod.lab5.entity.Task;
import by.pantosha.itirod.lab5.repository.SqlTaskRepository;
import by.pantosha.itirod.lab5.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(urlPatterns = "/*")
public class TaskServlet extends HttpServlet {
    private final static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/test";
    private final static String TITLE_TASK_PARAMETER = "title";
    private final static String SUBTASK_PARAMETER = "subtitle";
    private final static String PAGE_TEMPLATE =
            "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>Tasks</title>" +
                    "</head>" +
                    "<body>" +
                    "<h1>Tasks</h1>" +
                    "<section>" +
                    "<h2>New task</h2>" +
                    "<form method='post'>" +
                    "<p><input type='text' name='" + TITLE_TASK_PARAMETER + "' placeholder='Title' maxlength='100' required/>" +
                    "<fieldset>" +
                    "<p><input type='text' name='" + SUBTASK_PARAMETER + "' placeholder='Subtask 1' maxlength='1000'/>" +
                    "<p><input type='text' name='" + SUBTASK_PARAMETER + "' placeholder='Subtask 2' maxlength='1000'/>" +
                    "<p><input type='text' name='" + SUBTASK_PARAMETER + "' placeholder='Subtask 3' maxlength='1000'/>" +
                    "</fieldset>" +
                    "<input type='submit' value='Add'>" +
                    "</form>" +
                    "</section>" +
                    "<section>" +
                    "<div class='taskslist'>" +
                    "%s" +
                    "</div>" +
                    "</section>" +
                    "</body>" +
                    "</html>";
    private final static String DELETE_PARAMETER = "delete";
    private final static String TASK_TEMPLATE =
            "<div class='task'>" +
                    "<h2>%s <a href='?" + DELETE_PARAMETER + "=%d'>x</a></h2>" +
                    "%s" +
                    "</div>";
    private TaskRepository _taskRepository;

    @Override
    public void init() {
        _taskRepository = new SqlTaskRepository(CONNECTION_STRING);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(TITLE_TASK_PARAMETER) != null) {
            Task task = new Task();
            task.setTitle(request.getParameter(TITLE_TASK_PARAMETER));

            String[] subtaskNames = request.getParameterValues(SUBTASK_PARAMETER);
            for (String subtaskText : subtaskNames) {
                if (!subtaskText.isEmpty()) {
                    Subtask subtask = new Subtask();
                    subtask.setText(subtaskText);
                    task.addSubtask(subtask);
                }
            }
            _taskRepository.create(task);
        }
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("text/html");
        if (request.getParameter(DELETE_PARAMETER) != null) {
            int id = Integer.parseInt(request.getParameter(DELETE_PARAMETER));
            _taskRepository.delete(id);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Collection<Task> tasks = _taskRepository.readAll();
        for (Task task : tasks) {
            stringBuilder.append(taskToHtml(task));
        }
        PrintWriter writer = response.getWriter();
        writer.format(PAGE_TEMPLATE, stringBuilder.toString());
    }

    private String taskToHtml(Task task) {
        Collection<Subtask> subtasks = task.getSubtasks();
        StringBuilder stringBuilder = new StringBuilder();
        if (!subtasks.isEmpty()) {
            stringBuilder.append("<ul>");
            for (Subtask subtask : task.getSubtasks()) {
                stringBuilder.append("<li>");
                stringBuilder.append(subtask.getText());
            }
            stringBuilder.append("</ul>");
        }
        return String.format(TASK_TEMPLATE, task.getTitle(), task.getId(), stringBuilder.toString());
    }
}
