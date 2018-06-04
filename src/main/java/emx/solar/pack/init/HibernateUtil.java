package emx.solar.pack.init;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			StandardServiceRegistryBuilder registryBuilder = null;
			try {
				registryBuilder = new StandardServiceRegistryBuilder();
				Map<String, String> settings = new HashMap<>();
				settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
				settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5433/edu_product");
				settings.put("hibernate.connection.username", "postgres");
				settings.put("hibernate.connection.password", "admin");
				settings.put("hibernate.show_sql", "true");
				settings.put("hibernate.hbm2ddl.auto", "update");
				settings.put("hibernate.jdbc.lob.non_contextual_creation", "true");
				
				registryBuilder.applySettings(settings);
				registry = registryBuilder.build();

				MetadataSources sources = new MetadataSources(registry);

				Metadata metadata = sources.getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				System.out.println("SessionFactory not created !!");
				if (registry != null)
					StandardServiceRegistryBuilder.destroy(registry);
			}
		}
		return sessionFactory;

	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
