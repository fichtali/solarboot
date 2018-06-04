package emx.solar.pack.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

/**
 * 17/05/2018
 * @author MFIC
 */
public interface IGenericHibernateDao<T, ID extends Serializable> {
		
	public T insert(T entity);
	public void saveOrUpdate(T entity);
	//public void update(T entity);
	public void delete(T entity);
	public void deleteById(ID id);
	public void saveAll(List<T> liste);
	public List<T> findAll(Order order);
	public T getById(ID id);
	
}
