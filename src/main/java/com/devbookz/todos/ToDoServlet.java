package com.devbookz.todos;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ToDoServlet
 */
@WebServlet({ "/ToDoServlet", "/todos" })
public class ToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NOINPUT="Please, enter TODO!";
	private String errorMessage;
	private List<ToDo> todos;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ToDoServlet() {
		super();
		todos = new LinkedList<>();
		errorMessage="";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// generate output
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String button = request.getParameter("button");

		switch (button) {

		case "save":
			actionAddToDo(request,response);
			
			break;
		case "reset":
			actionReset(request,response);
		}
		
	}
	
	private synchronized void actionReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		todos.clear();
		errorMessage="";
		processRequest(request, response);				
	}
	
	private synchronized void actionAddToDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get post parameter
		String todoDescr = request.getParameter("todo");
		if (todoDescr != null && !todoDescr.isEmpty()) {
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());

			// add todo list
			todos.add(todo);
			errorMessage = "";
		} else {
			// Error message
			errorMessage = "<span style=\"color:red\">"+NOINPUT+"</span>";
		}		
		processRequest(request, response);	
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().print(generateOutput());
	}

	private String generateTable() {
		StringBuffer table = new StringBuffer();
		for (ToDo todo : todos) {
			table.append("<tr><td>").append(todo.getDescription()).append("</td>");
			table.append("<td>").append(todo.getCreated()).append("</td></tr>");
		}
		if (table.length() == 0)
			table.append("<tr></tr>");
		return table.toString();
	}

	private String generateOutput() {
		StringBuffer site = new StringBuffer();

		site.append("<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<title>My TODOs</title>"
				+ "<link rel=\"stylesheet\" href=\"\">"
				+ "</head>"
				+ "<body>"
				+ "<div id=\"header\">"
				+ "<p><b>My-TODOs</b> - Little Helper</p>"
				+ "</div>"
				+ "<div id=\"container\""
				+ "<div id=\"content\">"
				+ "<h1>Enter TODO</h1>"
				+ "<form  method=\"POST\" action=\"\">"
				+ "<input type=\"text\" name=\"todo\" class=\"form-control\" size=\"50\" placeholder=\"Enter todo\" />"
				+ "<button type=\"submit\" name=\"button\" value=\"save\">Save</button>" 
				+ errorMessage 
				+ "<h1>My TODOs</h1>"
				+ "<table>"
				+ generateTable()
				+ "</table>"
				+ "<button type=\"submit\" name=\"button\" value=\"reset\">Reset</button>"
				+ "</form>" + "</div>" + "<div id=\"footer\">"
				+ "<p>(C) 2014 geakleap ltd., MIT Licence</p>" + "</div>"
				+ "</body>" + "</html>");

		return site.toString();

	}

}
