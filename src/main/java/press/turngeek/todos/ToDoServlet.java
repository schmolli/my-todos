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
import javax.servlet.http.HttpSession;

@WebServlet({ "/ToDoServlet", "/todos" }) 
public class ToDoServlet extends HttpServlet {

	private static final long serialVersionUID = -7843898075264520941L;
	private ServletConfig config;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config=config;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// no action
		sendResponse(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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
			request.setAttribute("err", "Please, enter TODO!");
		}
	}

	private void sendResponse(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		config.getServletContext().getRequestDispatcher("/WEB-INF/todos.jsp").forward(request, response);
	}

	//TODO Keys for attributes should use press.turngeek.todos
	private List<ToDo> getTodos(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<ToDo> todos = (List<ToDo>)session.getAttribute("todos");
		if (todos==null) {
			todos=new LinkedList<ToDo>();
			session.setAttribute("todos", todos); 
		}
		return todos;
	}
}