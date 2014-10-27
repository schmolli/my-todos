package com.devbookz.todos;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

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
    private List<ToDo> todos;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToDoServlet() {
        super();
        todos=new Vector<>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//generate output
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get post parameter
		String todoDescr = request.getParameter("todo");
		if (todoDescr!=null&&todoDescr.length()>0) {
			//Save button
			//create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());
			
			//add todo list
			todos.add(todo);			
		} else {
			//Reset button
			todos.clear();
		}
		//generate output
		processRequest(request, response);
	}
		

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().print(generateOutput());
	}
	
	private String generateTable() {
		StringBuffer table = new StringBuffer();
		for (ToDo todo : todos) {
			table.append("<tr><td>"+todo.getDescription()+"</td>");
			table.append("<td>"+todo.getCreated()+"</td></tr>");
		}
		if (table.length()==0) table.append("<tr><td>Currently, there are no TODOs for you!</td></tr>");
		return table.toString();
	}
	
	private String generateOutput() {
		StringBuffer site = new StringBuffer();
		
		site.append("<!DOCTYPE html>" +
		"<html>" +
		"<head>" +
			"<meta charset=\"UTF-8\">" +
			"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
			"<title>My TODOs</title>" +
			"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">" +
		"</head>" +
		"<body>" +
		"<div class=\"container\"" +
			"<section>" +
				"<h1>Enter TODO</h1>" +
				"<form role=\"form\" method=\"POST\" action=\"\">" +
		  			"<div class=\"form-group\">" +
						"<input type=\"text\" name=\"todo\" class=\"form-control\" size=\"50\" placeholder=\"Enter todo\" />" + 
						"<button type=\"submit\" id=\"save\" class=\"btn btn-default\">Save</button>" +
					"</div>" +
				"</form>" +
			"</section>" +
			"<section>" +
				"<h1>My TODOs</h1>" +
				"<table class=\"table table-striped\">" +
					generateTable() +
				"</table>" +
				"<form role=\"form\" action=\"\">" +
					"<div class=\"form-group\">" + 
						"<button type=\"submit\" id=\"reset\" formmethod=\"POST\" class=\"btn btn-default\">Reset</button>"+
					"</div>" +
				"</form>" +
			"</section>" +
			"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js\"></script>" +
			"</div" +
			"</body>" +
		"</html>");
		
		
		return site.toString();
		
	}

}
