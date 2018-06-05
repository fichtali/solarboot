package emx.solar.pack.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

/**
 * 17/05/2018
 * @author MFIC
 */
public interface IGenericHibernateDao<T, ID extends Serializable> {
		
	public T findOne(final ID id);
	public List<T> findAll(Order order);
	public T insert(final T entity);
	public void update(final T entity);
	public void delete(final T entity);
	public void deleteById(final ID id);
	public void saveAll(List<T> list);
	
}
