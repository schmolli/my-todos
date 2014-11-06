<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="todos" class="press.turngeek.todos.MyToDosBean" scope="session"/>
<c:if test="${!empty param.todo}">
	<jsp:setProperty property="todo" name="todos" value="${param.todo}"/>
</c:if>
<c:if test="${param.button=='reset'}">
		${todos.reset()}
</c:if>
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
			<form method="POST" >
				<input type="text" name="todo" size="30" placeholder="Enter TODO" />
				<button type="submit" name="button" value="save">Save</button>
				<c:if test="${empty param.todo && param.button=='save'}">
					<span style="color:red">					
						Please, enter TODO!
					</span>
				</c:if>
				<h1>My TODOs</h1>
				<table>
					<c:forEach items="${todos.todos}" var="todo">
						<tr>
							<td><c:out value="${todo.description}"/></td>
							<td><c:out value="${todo.created}"/></td>
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