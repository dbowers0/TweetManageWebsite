<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% String textValue=(String)request.getAttribute("Prediction"); %>
Prediction of tweet is: <%=textValue%><br>
<a href="./index2.html">Start Over!</a> <br>
</body>
</html>