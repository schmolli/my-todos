package press.turngeek.todos;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
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
	
	private static final long serialVersionUID = -1217343668262675847L;
	private ServletConfig config; 
	private List<ToDo> todos = new LinkedList<>();//TODO move list in session context

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config=config;
	}

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionListToDos(request);
		config.getServletContext().getRequestDispatcher("/WEB-INF/todos.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		config.getServletContext().getRequestDispatcher("/WEB-INF/todos.jsp").forward(request, response);
	}

}
