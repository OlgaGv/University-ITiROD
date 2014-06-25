<%@tag display-name="Task detailed view" description="Task Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="d" uri="/WEB-INF/libs/date.tld" %>
<%@attribute name="task" required="true" type="by.pantosha.itirod.lab5.entity.Task" %>

<div class="item">
    <div class="content">
        <div class="meta"><d:dateprinttag date="${task.creationDate}"/></div>
        <div class="name"><c:out value="${task.title}"/></div>
        <div class="description">
            <ul class="ui list">
                <c:forEach var="subtask" items="${task.subtasks}">
                    <li><c:out value="${subtask.text}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="extra">
        <a href="removetask.jsp?id=${task.id}">Remove</a>
    </div>
</div>