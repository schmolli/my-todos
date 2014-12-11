package press.turngeek.todos.service;

import java.util.List;
import java.util.Vector;

import javax.ejb.Stateful;

import press.turngeek.todos.model.ToDo;

@Stateful
public class ToDoServiceBean implements ToDoService {
	
	private List<ToDo> todos;
	
	public ToDoServiceBean() {
		super();
		todos=new Vector<ToDo>();
	}

	@Override
	public List<ToDo> getAllToDos() {
		return todos;
	}

	@Override
	public void addToDo(ToDo todo) {
		todos.add(todo);
	}

	@Override
	public void resetToDos() {
		todos.clear();
	}
}
