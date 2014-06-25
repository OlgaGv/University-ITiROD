<%@ page import="by.pantosha.itirod.lab5.entity.Subtask" %>
<%@ page import="by.pantosha.itirod.lab5.entity.Task" %>
<%@ page import="by.pantosha.itirod.lab5.repository.SqlTaskRepository" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
    private final static String TASK_TITLE_PARAMETER = "title";
    private final static String SUBTASK_PARAMETER = "subtask";
%>

<%
    request.setCharacterEncoding("UTF-8");
    if ("POST".equals(request.getMethod())) {
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
            new SqlTaskRepository("jdbc:mysql://localhost:3306/test").create(task);
            response.sendRedirect("index.jsp");
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
        <form class="ui form segment" method="post">
            <div class="field">
                <label>Title</label>

                <div class="ui input">
                    <input type="text" name="title" placeholder="Task 1" required="true">

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
                <a class="ui button" href="<c:url value="index.jsp"/>">Cancel</a>
                <input class="ui positive button" type="submit" value="Add"/>
            </div>
        </form>
    </jsp:body>
</t:genericpage>