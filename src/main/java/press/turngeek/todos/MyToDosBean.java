package press.turngeek.todos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MyToDosBean {
	
	private List<ToDo> todos;

	public MyToDosBean() {
		super();
		todos=new LinkedList<>();
	}

	public List<ToDo> getTodos() {
		return todos;
	}
	
	public void setTodo(String todo) {
		ToDo newToDo = new ToDo();
		newToDo.setDescription(todo);
		newToDo.setCreated(new Date());
		todos.add(newToDo);
	}
	
	public void reset() {
		todos.clear();
	}

}
