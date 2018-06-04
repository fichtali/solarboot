package emx.solar.pack.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import emx.solar.pack.dao.IGenericHibernateDao;

@Repository
public abstract class GenericHibernateDaoImpl<T, ID extends Serializable> implements IGenericHibernateDao<T, ID> {

	protected static final int LIGNES_PAR_PAGES = 25;

	protected Class<? extends T> clazz;

	@SuppressWarnings("unchecked")
	public GenericHibernateDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		clazz = (Class) pt.getActualTypeArguments()[0];
	}

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * protected Session getCurrentSession() { return
	 * sessionFactory.getCurrentSession(); }
	 */

	@Override
	public T insert(T entity) {
		entityManager.persist(entity);
		return entity;

	}

	@Override
	public void saveOrUpdate(T entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	@Override
	public void saveAll(List<T> liste) {
		for (T t : liste)
			insert(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Order order) {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@Override
	public T getById(ID id) {
		return (T) entityManager.find(clazz, id);
	}

	@Override
	public void deleteById(ID id) {
		T e = getById(id);
		if (e != null)
			entityManager.remove(e);
	}

}
