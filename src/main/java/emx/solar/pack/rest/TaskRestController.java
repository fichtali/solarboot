package emx.solar.pack.rest;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import emx.solar.pack.dao.ITaskDao;
import emx.solar.pack.entities.Task;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class TaskRestController {

	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello Mehdi";
	}

	//@Autowired
	//private TaskRepository taskRepo;
	
	@Autowired
	private ITaskDao itask;
	
	@GetMapping("/tasks")
	public List<Task> listOfTasks(){
		return itask.findAll(Order.asc("taskName"));
	}
	
	/*@PostMapping("/task")
	public Task saveTask(@RequestBody Task t) {
		//return taskRepo.save(t);
	}*/
	
}
