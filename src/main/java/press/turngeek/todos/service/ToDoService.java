package press.turngeek.todos.service;

import java.util.List;

import press.turngeek.todos.model.ToDo;

public interface ToDoService {
	
	public List<ToDo> getAllToDos();
	public void addToDo(ToDo todo);
	public void reset();

}
