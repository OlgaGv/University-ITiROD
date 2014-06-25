<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" class="ui" href="<c:url value="/resource/css/semantic.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/tasks.css"/>">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
</head>
<body id="tasks">
<header>
    <jsp:invoke fragment="header"/>
</header>
<jsp:doBody/>
</body>
</html>