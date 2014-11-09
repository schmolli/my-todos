package press.turngeek.todos;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/ToDoServlet", "/todos" }) 
public class ToDoServlet extends HttpServlet {

	private static final long serialVersionUID = -7843898075264520941L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// generate output
		sendResponse(response, actionListToDos(request));
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String output;
		// get post parameter
		final String button = request.getParameter("button");
		switch (button) {
		case "reset":
			output = actionReset(request);
			sendResponse(response, output);
			break;
		case "save":
			String todoDescr = request.getParameter("todo");
			output = actionAddToDo(request, todoDescr);
			sendResponse(response, output);
			break;
		default:
			doGet(request, response);
		}
		
	}

	private synchronized String actionReset(HttpServletRequest request) {
		List<ToDo> todos=getTodos(request);
		todos.clear();
		return generateOutput(request);
	}

	private synchronized String actionAddToDo(HttpServletRequest request, String todoDescr) {

		if (todoDescr != null && !todoDescr.isEmpty()) {
			List<ToDo> todos=getTodos(request);
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());

			// add todo list
			todos.add(todo);
			return generateOutput(request);
		} else {
			return generateOutput("Please, enter TODO!",request);
		}
	}
	
	private synchronized String actionListToDos(HttpServletRequest request) {
		return generateOutput(request);
	}

	private void sendResponse(HttpServletResponse response, final String output)
			throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().print(output);
	}

	private List<ToDo> getTodos(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<ToDo> todos = (List<ToDo>)session.getAttribute("todos");
		if (todos==null) {
			todos=new LinkedList<ToDo>();
			session.setAttribute("todos", todos); 
		}
		return todos;
	}
	
	private String generateTable(HttpServletRequest request) {
		List<ToDo> todos=getTodos(request);
		StringBuilder table = new StringBuilder();
		for (ToDo todo : todos) {
			table.append("<tr><td>").append(todo.getDescription())
					.append("</td>");
			table.append("<td>").append(todo.getCreated()).append("</td></tr>");
		}
		if (table.length() == 0)
			table.append("<tr></tr>");
		return table.toString();
	}

	private String generateOutput(HttpServletRequest request) {
		return generateOutput("", request);
	}
	//TODO StrinBuilder?
	//TODO Read from File?
	private String generateOutput(String errorMessage, HttpServletRequest request) {
		String site = "<!DOCTYPE html>"
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
				+ "<button type=\"submit\" name=\"button\" value=\"save\">Save</button>";
			if (!errorMessage.isEmpty())
				site += "<span style=\"color:red\">" + errorMessage +"</span>";
			site+=
				"<h1>My TODOs</h1>"
				+ "<table>"
				+ generateTable(request)
				+ "</table>"
				+ "<button type=\"submit\" name=\"button\" value=\"reset\">Reset</button>"
				+ "</form>" + "</div>" + "<div id=\"footer\">"
				+ "<p>(C) 2014 Schiesser/Schmollinger, MIT Licence</p>" + "</div>"
				+ "</body>" + "</html>";

		return site;

	}

}
