<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Restaurants</h2>
<section>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Restaurants</th>
        <th>Rating</th>
        <th>Dishes</th>
    </tr>
    </thead>
    <c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" type="com.newsirius.voting.model.Restaurant"/>
        <tr>
            <td>${restaurant.name}</td>
            <td>${restaurant.voteRating}</td>
            <td>${restaurant.dishes}</td>
        </tr>
    </c:forEach>
</table>
</section>
</body>
</html>
