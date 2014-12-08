<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="press.turngeek.todos.ToDo"%>

<jsp:useBean id="press.turngeek.todos.todos" class="java.util.LinkedList<ToDo>" scope="session"/>
<jsp:useBean id="press.turngeek.todos.err" class="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My TODOs</title>
<!-- <link rel="stylesheet" href=""> -->
</head>
<body>
	<div id="container">
		<div id="header">
			<p>
				<b>My-TODOs</b> – Little Helper
			</p>
		</div>
		<div id="content">
			<h1>Enter TODO</h1>
			<form method="POST" action="todos">
				<input type="text" name="todo" size="30" placeholder="Enter TODO" />
				<button type="submit" name="button" value="save">Save</button>
				<c:if test="${!empty errorMessage}">
					<span style="color: red"><%=errorMessage%></span>
				</c:if>
				<h1>My TODOs</h1>
				<table>
					<c:forEach items="${todos}" var="todo">
						<tr>
							<td>
								<%=todo.description%>
							</td>
							<td>
								<%=todo.created%>
							</td>
						</tr>
					</c:forEach>
				</table>
				<button type="submit" name="button" value="reset">Reset</button>
			</form>
		</div>
		<div id="footer">
			<p>(C) 2014 Schießer/Schmollinger, MIT license</p>
		</div>
	</div>
</body>
</html>