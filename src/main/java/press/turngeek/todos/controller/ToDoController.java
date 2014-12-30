package press.turngeek.todos.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import press.turngeek.todos.model.ToDo;
import press.turngeek.todos.service.ToDoService;

@ManagedBean
@ViewScoped
public class ToDoController implements Serializable {
	
	private static final long serialVersionUID = 8994022512914167890L;
	private ToDo todo;
	
	@EJB
	private ToDoService toDoService;
		
	public ToDoController() {
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
		toDoService.addToDo(newTodo);
		todo.setDescription("");
		return "todos";
	}
	
	public String doReset() {		
		toDoService.resetToDos();
		return "todos";
	}
	
	public List<ToDo> getTodos() {
		return toDoService.getAllToDos();
	}

}
