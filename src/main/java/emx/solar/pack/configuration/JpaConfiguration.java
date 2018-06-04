package emx.solar.pack.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import emx.solar.pack.utils.AppGlobalConfig;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = AppGlobalConfig.BASE_PACKAGE, entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class JpaConfiguration {

	private static final boolean GENERATED_DDL = true;
	private static final String[] ENTITY_PACKAGES = { "emx.solar.pack.entities" };

	@Autowired
	private Environment env;

	@Value("${datasource.productapp.maxPoolSize:10}")
	private int maxPoolSize;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.productapp")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource datasource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource datasource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName()).url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword())
				.type(HikariDataSource.class).build();
		datasource.setMaximumPoolSize(maxPoolSize);
		// another config datasource
		/*
		 * DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 * dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		 * dataSource.setUrl(dataSourceProperties.getUrl());
		 * dataSource.setUsername(dataSourceProperties.getUsername());
		 * dataSource.setPassword(dataSourceProperties.getPassword());
		 */
		return datasource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	/*
	 * @Bean public LocalSessionFactoryBean sessionFactory() {
	 * LocalSessionFactoryBean em = new LocalSessionFactoryBean();
	 * em.setDataSource(datasource()); em.setPackagesToScan(ENTITY_PACKAGES);
	 * //em.setHibernateProperties(hibernateProperties()); return em; }
	 */

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(datasource());
		em.setPackagesToScan(AppGlobalConfig.ENTITY_PACKAGE);
		em.setJpaProperties(jpaProperties());
		em.setJpaVendorAdapter(jpaVendorAdapter());
		return em;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(AppGlobalConfig.GENERATED_DDL);
		return vendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();

		// Configures the used database dialect. This allows Hibernate to create SQL
		// that is optimized for the used database.
		properties.put("hibernate.dialect", env.getRequiredProperty("datasource.productapp.hibernate.dialect"));

		// Specifies the action that is invoked to the database when the Hibernate
		// SessionFactory is created or closed.
		properties.put("hibernate.hbm2ddl.auto",
				env.getRequiredProperty("datasource.productapp.hibernate.hbm2ddl.method"));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		properties.put("hibernate.show_sql", env.getRequiredProperty("datasource.productapp.hibernate.show_sql"));

		// If the value of this property is true, Hibernate will use prettyprint
		// when it writes SQL to the console.
		properties.put("hibernate.format_sql", env.getRequiredProperty("datasource.productapp.hibernate.format_sql"));

		properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
		return properties;
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager() {
		/*
		 * HibernateTransactionManager txManager = new HibernateTransactionManager();
		 * txManager.setSessionFactory(sessionFactory); return txManager;
		 */

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
