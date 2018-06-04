package emx.solar.pack.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class TaskRestController {

	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello Mehdi";
	}

	//private TaskRepository taskRepo;
	
	/*@GetMapping("/tasks")
	public List<Task> listOfTasks(){
		//return taskRepo.findAll();
	}
	
	@PostMapping("/task")
	public Task saveTask(@RequestBody Task t) {
		//return taskRepo.save(t);
	}*/
	
}
