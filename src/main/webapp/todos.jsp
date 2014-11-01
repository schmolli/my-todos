<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="press.turngeek.todos.ToDo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Date" %>
<%! 
	private static final String NOINPUT = "Please, enter TODO!";
	private List<ToDo> todos=new LinkedList<>();
	private synchronized void actionReset() {
		todos.clear();		
	}
	private synchronized String actionAddToDo(String todoDescr) {
	if (todoDescr != null && !todoDescr.isEmpty()) {
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());
			// add todo list
			todos.add(todo);
			return "";
		} else {
			//set error
			return NOINPUT;
		}
	}
	private String generateTable() {
		StringBuilder table = new StringBuilder();
		for (ToDo todo : todos) {
			table.append("<tr><td>").append(todo.getDescription()).append("</td>");
			table.append("<td>").append(todo.getCreated()).append("</td></tr>");
		}
		if (table.length() == 0)
			table.append("<tr></tr>");
		return table.toString();
	}
%>

<%
	final String button = request.getParameter("button");
	//if no button is pressed it is probably the initial GET to the page
	if (button!=null) {
		switch (button) {
		case "reset":
			actionReset();
			request.setAttribute("errorMessage", "");
			request.setAttribute("table","");
			break;
		case "save":
		default:
			String todoDescr = request.getParameter("todo");
			String errorMessage=actionAddToDo(todoDescr);
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("table",generateTable());
			break;
		} 	
	} else {
		request.setAttribute("errorMessage", "");
		request.setAttribute("table",generateTable());
	}
%>
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
				<span style="color:red"><%=request.getAttribute("errorMessage") %></span>
				<h1>My TODOs</h1>
				<table>
					<%=request.getAttribute("table")%> 
				</table>
				<button type="submit" name="button" value="reset">Reset</button>
			</form>
		</div>
		<div id="footer">
			<p>(C) 2014 turngeek ltd., MIT license</p>
		</div>
	</div>
</body>
</html>