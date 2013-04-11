/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main configuration class for the application. Turns on @Component scanning,
 * loads externalized application.properties, and sets up the database.
 * 
 */
@Configuration
@ComponentScan(basePackages = "zho.com.fw", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:zho/com/fw/config/application.properties")
@EnableTransactionManagement
public class MainConfig {

	@Inject
	private Environment environment;

	@Value("${jdbc.driverClassName}")
	private String driverClassName;

	@Value("${jdbc.databaseurl}")
	private String url;

	@Value("${hibernate.dialect}")
	String hibernateDialect;

	@Value("${hibernate.show_sql}")
	boolean hibernateShowSql;

	@Value("${hibernate.hbm2ddl.auto}")
	String hibernateHbm2ddlAuto;

	@Bean
	public DataSource dataSourceMysql() {

		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
	}

	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
		factory.setDatabaseName("spring-social-showcase");
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		factory.setDatabasePopulator(databasePopulator());
		return factory.getDatabase();
	}
	
	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("JdbcUsersConnectionRepository.sql", JdbcUsersConnectionRepository.class));
		return populator;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(sessionFactory().getObject());
		return tx;
	}

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		final AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSourceMysql());
		sessionFactory.setPackagesToScan(new String[]{"zho.com.fw.bean"});
		sessionFactory.setHibernateProperties(this.hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// internal helpers

	final Properties hibernateProperties() {
		return new Properties() {
			{
				this.put("persistence.dialect", "org.hibernate.dialect.MySQLDialect");
				this.put("hibernate.show_sql", "true");
			}
		};
	}
}
