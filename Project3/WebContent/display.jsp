<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <% String textValue=(String)request.getAttribute("username"); %>
 <% String textValue2=(String)request.getAttribute("tweetUser"); %>
 ids for the username <%=textValue2%> are: <%=textValue%>

  <br>

</body>
</html>