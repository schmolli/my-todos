package press.turngeek.todos.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import press.turngeek.todos.data.ToDoManager;
import press.turngeek.todos.model.ToDo;

@RequestScoped
@Transactional(value=Transactional.TxType.REQUIRED)
public class ToDoServiceBean implements ToDoService {

	@Inject
	ToDoManager manager;

	@Override
	public List<ToDo> getAllToDos() {
		List<ToDo> todos = manager.findAllToDos();
		return todos;
	}

	@Override
	public void addToDo(ToDo todo) {
		List<ToDo> todos = manager.findAllToDos();
		todos.add(todo);
	}

	@Override
	public void resetToDos() {
		List<ToDo> todos = manager.findAllToDos();
		todos.clear();
	}
}
