package emx.solar.pack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emx.solar.pack.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
