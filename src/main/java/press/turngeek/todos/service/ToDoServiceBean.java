package press.turngeek.todos.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import press.turngeek.todos.data.ToDoManager;
import press.turngeek.todos.model.ToDo;

@Stateless
public class ToDoServiceBean implements ToDoService {
	
	@EJB
	ToDoManager provider;

	@Override
	public List<ToDo> getAllToDos() {
		List<ToDo> todos = provider.findAllToDos();
		return todos;
	}

	@Override
	public void addToDo(ToDo todo) {
		List<ToDo> todos = provider.findAllToDos();
		todos.add(todo);
	}

	@Override
	public void resetToDos() {
		List<ToDo> todos = provider.findAllToDos();
		todos.clear();
	}
}
