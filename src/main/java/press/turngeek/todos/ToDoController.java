package press.turngeek.todos;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name="toDoController")
public class ToDoController  {
	
	private ToDo todo;
	private List<ToDo> todos;
		
	public ToDoController() {
		todos=new Vector<>();
		todo=new ToDo();
	}

	public ToDo getTodo() {
		return todo;
	}

	public void setTodo(ToDo todo) {
		this.todo = todo;
	}
	
	public String  doSave() {
		ToDo newTodo = new ToDo();
		newTodo.setDescription(todo.getDescription());
		newTodo.setCreated(new Date());
		todos.add(newTodo);
		todo.setDescription("");
		return "todos";
	}
	
	public String doReset() {		
		todos.clear();
		return "todos";
	}
	
	public List<ToDo> getTodos() {
		return todos;
	}

}
