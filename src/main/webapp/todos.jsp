<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="press.turngeek.todos.ToDo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.Date"%>
<%!
	private List<ToDo> todos = new LinkedList<>();//TODO move list in session context

	private synchronized void actionReset(HttpServletRequest request) {
		todos.clear();
		request.setAttribute("errorMessage", "");
		request.setAttribute("todos", new LinkedList<ToDo>());
	}

	private synchronized void actionAddToDo(HttpServletRequest request) {
		String todoDescr = request.getParameter("todo");
		if (todoDescr != null && !todoDescr.isEmpty()) {
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());
			// add todo list
			todos.add(todo);
			request.setAttribute("errorMessage", "");
			request.setAttribute("todos", new LinkedList<ToDo>(todos));
		} else {
			request.setAttribute("errorMessage", "Please, enter TODO!");
			request.setAttribute("todos", new LinkedList<ToDo>(todos));
		}
	}
	
	private synchronized void actionListToDos(HttpServletRequest request) {
		request.setAttribute("errorMessage", "");
		request.setAttribute("todos", new LinkedList<ToDo>(todos));
	}
%>

<%
	final String button = request.getParameter("button");
	//if no button is pressed it is the initial GET to the page
	if (button != null) {
		switch (button) {
		case "reset":
			actionReset(request);
			break;
		case "save":
			actionAddToDo(request);
			break;
		default:
			actionListToDos(request);
		}
	} else {
		actionListToDos(request);
	}
%>
<jsp:useBean id="todos" class="java.util.LinkedList<ToDo>" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
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
			<form method="POST" action="todos.jsp">
				<input type="text" name="todo" size="30" placeholder="Enter TODO" />
				<button type="submit" name="button" value="save">Save</button>
				<c:if test="${errorMessage!=''}">
					<span style="color: red">${errorMessage}</span>
				</c:if>
				<h1>My TODOs</h1>
				<table>
					<c:forEach items="${todos}" var="todo">
						<tr>
							<td>
								${todo.description} 
							</td>
							<td>
								${todo.created}
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