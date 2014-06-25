<%@ page import="by.pantosha.itirod.lab5.repository.SqlTaskRepository" %>
<%!
    private final String TASK_ID_PARAMETER = "id";
%>
<%
    String parameterTaskId = request.getParameter(TASK_ID_PARAMETER);
    try {
        int taskId = Integer.parseInt(parameterTaskId);
        new SqlTaskRepository("jdbc:mysql://localhost:3306/test").delete(taskId);
    } finally {
        response.sendRedirect("index.jsp");
    }
%>