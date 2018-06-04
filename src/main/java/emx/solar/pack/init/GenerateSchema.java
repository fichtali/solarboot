package emx.solar.pack.init;

import org.hibernate.Session;

public class GenerateSchema {

	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		HibernateUtil.shutdown();
	}

}
