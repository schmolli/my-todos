package press.turngeek.todos.data;

import java.util.List;
import java.util.Vector;

import javax.ejb.Stateful;

import press.turngeek.todos.model.ToDo;


@Stateful
public class ToDoManager {
	
	private List<ToDo> todos;

	public ToDoManager() {
		super();
		todos=new Vector<ToDo>();
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
