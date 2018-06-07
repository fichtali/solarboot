package emx.solar.pack.dao.impl;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import emx.solar.pack.entities.Task;

@Repository
@Transactional
public class TaskDao extends GenericHibernateDaoImpl<Task, Long> {
	
	public List<Task> findByName(String name) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = builder.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		query.select(root).where(builder.like(root.get("taskName"), "%"+name+"%"));
		Query<Task> q = (Query<Task>) entityManager.createQuery(query);
		return q.getResultList();
	}

}
