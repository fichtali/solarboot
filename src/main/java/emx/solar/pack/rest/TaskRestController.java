package emx.solar.pack.rest;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emx.solar.pack.dao.impl.TaskDao;
import emx.solar.pack.entities.Task;
import emx.solar.pack.repositories.TaskRepository;
import emx.solar.pack.utils.Helper;

@RestController
@RequestMapping("/tasks")
@PreAuthorize("hasAuthority('ADMIN')")
public class TaskRestController {

	private static final Logger logger = LoggerFactory.getLogger(TaskRestController.class);
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping
	public List<Task> listOfTasks(){
		logger.info("find all tasks");
		return taskRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Task getUniqueTask(@PathVariable Long id) {
		logger.info("Param task by id : "+id);
		return taskDao.findOne(id);
	}
	
	@GetMapping("/findby")
	public List<Task> getTaskByName(@RequestParam(name="name", required=true, defaultValue="") String name) {
		logger.info("Param task : "+name );
		if(Helper.nullOuVide(name))
			return Collections.emptyList();
		return taskDao.findByName(name);
	}
	
	@PostMapping("/action")
	public Task saveTask(@RequestBody Task t) {
		logger.info(t.toString());
		return taskDao.insert(t);
	}
	
}
