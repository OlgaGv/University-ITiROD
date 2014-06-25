<%@tag description="Tasks Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="tasks" type="java.util.Collection<by.pantosha.itirod.lab5.entity.Task>" required="true" %>

<div class="ui items">
    <c:forEach var="task" items="${tasks}">
        <t:taskdetail task="${task}"/>
    </c:forEach>
</div>