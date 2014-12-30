package press.turngeek.todos.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import press.turngeek.todos.model.ToDo;;

@SessionScoped
public class ToDoManager implements Serializable {

	private static final long serialVersionUID = -3258831452772896931L;
	private List<ToDo> todos;

	@PostConstruct
	public void init() {
		todos = new Vector<ToDo>();
	}

	public List<ToDo> findAllToDos() {
		return todos;
	}

	public void persistToDo(ToDo todo) {
		todos.add(todo);
	}

	public void removeAllToDos() {
		todos.clear();
	}

}
