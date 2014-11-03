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

@WebServlet({ "/ToDoServlet", "/todos" })
public class ToDoServlet extends HttpServlet {

	private static final long serialVersionUID = -7843898075264520941L;
	private List<ToDo> todos;

	public ToDoServlet() {
		todos = new LinkedList<>();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// generate output
		sendResponse(response, generateOutput());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String output;
		// get post parameter
		final String button = request.getParameter("button");
		switch (button) {
		case "reset":
			output = actionReset();
			break;
		case "save":
			String todoDescr = request.getParameter("todo");
			output = actionAddToDo(todoDescr);
			break;
		default:
			output=generateOutput();
		}
		sendResponse(response, output);
	}

	private synchronized String actionReset() {
		todos.clear();
		return generateOutput();
	}

	private synchronized String actionAddToDo(String todoDescr) {

		if (todoDescr != null && !todoDescr.isEmpty()) {
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());

			// add todo list
			todos.add(todo);
			return generateOutput();
		} else {
			return generateOutput("Please, enter TODO!");
		}
	}

	private void sendResponse(HttpServletResponse response, final String output)
			throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().print(output);
	}

	private String generateTable() {
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

	private String generateOutput() {
		return generateOutput("");
	}
	private String generateOutput(String errorMessage) {
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
				+ "<button type=\"submit\" name=\"button\" value=\"save\">Save</button>"
				+ "<span style=\"color:red\">" + errorMessage +"</span>"
				+ "<h1>My TODOs</h1>"
				+ "<table>"
				+ generateTable()
				+ "</table>"
				+ "<button type=\"submit\" name=\"button\" value=\"reset\">Reset</button>"
				+ "</form>" + "</div>" + "<div id=\"footer\">"
				+ "<p>(C) 2014 Schießer/Schmollinger, MIT Licence</p>" + "</div>"
				+ "</body>" + "</html>";

		return site;

	}

}
