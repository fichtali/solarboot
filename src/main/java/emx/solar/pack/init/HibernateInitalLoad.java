package emx.solar.pack.init;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateInitalLoad {

	public static void main(String[] args) {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			if(!tx.isActive())
				tx.begin();

			/*UserRole u1= new UserRole();
			u1.setType(UserRoleType.DBA);


			
			session.saveOrUpdate(u1);

			
			User u = new User();
			u.setEmail("almoujaddid.hind@gmail.com");
			u.setLastName("Hind");
			u.setFirstName("ALMOUJADDID");
			u.setPassword("p@ssword");
			u.setSsoID("1254log4j");
			u.setState(StateEnum.ACTIVE);
			Set<UserRole> urs = new HashSet<>();
			urs.add(u1);
			u.setRoles(urs);
			
			session.saveOrUpdate(u);*/

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		HibernateUtil.shutdown();

	}

}
