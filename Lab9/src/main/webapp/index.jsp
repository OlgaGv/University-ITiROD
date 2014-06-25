<%@ page import="by.pantosha.itirod.lab5.entity.Task" %>
<%@ page import="by.pantosha.itirod.lab5.repository.SqlTaskRepository" %>
<%@ page import="java.util.Collection" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    Collection<Task> tasks = new SqlTaskRepository("jdbc:mysql://localhost:3306/test").readAll();
    pageContext.setAttribute("tasks", tasks);
%>

<t:genericpage>
    <jsp:attribute name="title">
        Tasks
    </jsp:attribute>
    <jsp:attribute name="header">
        <h1 class="ui header">
            <i class="attachment icon"></i>
            Tasks
        </h1>
    </jsp:attribute>
    <jsp:body>
        <t:tasks tasks="${tasks}"/>
        <div class="newtask">
            <a href="addtask.jsp">Add task...</a>
        </div>
    </jsp:body>
</t:genericpage>