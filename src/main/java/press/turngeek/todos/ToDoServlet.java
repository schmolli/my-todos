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
		// no action
		sendResponse(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get post parameter
		final String button = request.getParameter("button");
		switch (button) {
		case "reset":
			actionReset(request);
			sendResponse(request, response);
			break;
		case "save":
			String todoDescr = request.getParameter("todo");
			actionAddToDo(request, todoDescr);
			sendResponse(request, response);
			break;
		default:
			// no action
			sendResponse(request, response);
		}	
	}

	private synchronized void actionReset(HttpServletRequest request) {
		List<ToDo> todos=getTodos(request);
		todos.clear();
	}

	private synchronized void actionAddToDo(HttpServletRequest request, String todoDescr) {

		if (todoDescr != null && !todoDescr.isEmpty()) {
			List<ToDo> todos=getTodos(request);
			// create todo
			ToDo todo = new ToDo();
			todo.setDescription(todoDescr);
			todo.setCreated(new Date());
			// add todo list
			todos.add(todo);
		} else {
			//set error message in request
			request.setAttribute("press.turngeek.todos.err", "Please, Enter TODO!");
		}
	}

	private void sendResponse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().print(renderResponse(request));
	}

	private List<ToDo> getTodos(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<ToDo> todos = (List<ToDo>)session.getAttribute("press.turngeek.todos.todos");
		if (todos==null) {
			todos=new LinkedList<ToDo>();
			session.setAttribute("press.turngeek.todos.todos", todos); 
		}
		return todos;
	}
	
	//TODO Bad visulization of resulting page!
	//TODO Improvement: Read from File? -> Exercise?
	private String renderResponse(HttpServletRequest request) {
		
		String part1 = "<!DOCTYPE html>"
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
		StringBuilder output = new StringBuilder(part1);
		String err = (String)request.getAttribute("press.turngeek.todos.err");
		System.out.println("err="+err);
		if (err!=null)
			output.append("<span style=\"color:red\">" + err + "</span>");

		String part2 = "<h1>My TODOs</h1>"
				+ "<table>";
		output.append(part2).append(renderTable(request));
		String part3 = "</table>"
				+ "<button type=\"submit\" name=\"button\" value=\"reset\">Reset</button>"
				+ "</form>" + "</div>" + "<div id=\"footer\">"
				+ "<p>(C) 2014 Schiesser/Schmollinger, MIT Licence</p>"
				+ "</div>" + "</body>" + "</html>";
		output.append(part3);

		return output.toString();
	}
	
	private String renderTable(HttpServletRequest request) {
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

}
