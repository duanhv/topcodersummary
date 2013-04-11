/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.showcase.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import javax.persistence.Persistence;
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
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.showcase.dao.AccountRepositoryImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main configuration class for the application. Turns on @Component scanning,
 * loads externalized application.properties, and sets up the database.
 * 
 * @author Craig Walls
 */
@Configuration
@ComponentScan(basePackages = "org.springframework.social.showcase", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:org/springframework/social/showcase/config/application.properties")
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
		// DriverManagerDataSource factory = new EmbeddedDatabaseFactory();
		// factory.setDatabaseName("spring-social-showcase");
		// factory.setDatabaseType(EmbeddedDatabaseType.H2);
		// factory.setDatabasePopulator(databasePopulator());
		// return factory.getDatabase();
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
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSourceMysql());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		final AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSourceMysql());
		sessionFactory.setPackagesToScan(new String[]{"org.springframework.social.showcase.bean"});
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
