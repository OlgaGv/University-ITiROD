<%@ page import="by.pantosha.itirod.lab10.xml.XmlTaskReader" %>
<%@ page import="by.pantosha.itirod.lab5.entity.Subtask" %>
<%@ page import="by.pantosha.itirod.lab5.entity.Task" %>
<%@ page import="by.pantosha.itirod.lab5.repository.SqlTaskRepository" %>
<%@ page import="by.pantosha.itirod.lab5.repository.TaskRepository" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
    private final static String TASK_TITLE_PARAMETER = "title";
    private final static String SUBTASK_PARAMETER = "subtask";
    private final static String ADD_TYPE_PARAMETER = "addType";
    private final XmlTaskReader xmlReader = new XmlTaskReader();
%>

<%
    request.setCharacterEncoding("UTF-8");
    if ("POST".equals(request.getMethod())) {
        String addType = request.getParameter(ADD_TYPE_PARAMETER);

        TaskRepository repository = new SqlTaskRepository("jdbc:mysql://localhost:3306/test");
        if ("xml".equals(addType)) {
            Collection<Task> tasks = xmlReader.read(request.getParameter("code"));
            if (!tasks.isEmpty()){
                for(Task task : tasks) {
                    repository.create(task);
                }
                response.sendRedirect("index.jsp");
            }
        } else {
            String taskTitle = request.getParameter(TASK_TITLE_PARAMETER);

            if (taskTitle != null && !taskTitle.isEmpty()) {
                Task task = new Task(taskTitle);
                String[] subtaskNames = request.getParameterValues(SUBTASK_PARAMETER);
                for (String subtaskText : subtaskNames) {
                    if (!subtaskText.isEmpty()) {
                        Subtask subtask = new Subtask();
                        subtask.setText(subtaskText);
                        task.addSubtask(subtask);
                    }
                }
                repository.create(task);
                response.sendRedirect("index.jsp");
            }
        }
    }
%>
<t:genericpage>
    <jsp:attribute name="header">
      <h1 class="ui header">
          <i class="plus icon"></i>
          New task
      </h1>
    </jsp:attribute>
    <jsp:body>
        <div class="ui two column middle aligned relaxed grid basic segment">
            <div class="column">
                <form class="ui form segment" method="post">
                    <div class="field">
                        <label>Title</label>

                        <div class="ui input">
                            <input type="text" name="title" placeholder="Subtask 1" required="true">

                            <div class="ui corner label">
                                <i class="icon asterisk"></i>
                            </div>
                        </div>
                    </div>
                    <div class="field">
                        <label>Subtask 1</label>

                        <div class="ui input">
                            <input type="text" name="subtask" placeholder="Subtask 1">
                        </div>
                    </div>
                    <div class="field">
                        <label>Subtask 2</label>

                        <div class="ui input">
                            <input type="text" name="subtask" placeholder="Subtask 2">
                        </div>
                    </div>
                    <div class="field">
                        <label>Subtask 3</label>

                        <div class="ui input">
                            <input type="text" name="subtask" placeholder="Subtask 3">
                        </div>
                    </div>
                    <div class="ui buttons">
                        <a class="ui large button" href="<c:url value="index.jsp"/>">Cancel</a>
                        <input type="hidden" name="addType" value="text"/>
                        <input class="ui positive button" type="submit" value="Add"/>
                    </div>
                </form>
            </div>
            <div class="ui vertical divider">Or</div>
            <div class="column">
                <form class="ui form segment" method="post">
                    <div class="field">
                        <label>Write XML</label>
                        <textarea name="code" required="true"></textarea>
                    </div>
                    <input type="hidden" name="addType" value="xml"/>
                    <input class="ui positive button" type="submit" value="Add"/>
                </form>
            </div>
        </div>
    </jsp:body>
</t:genericpage>