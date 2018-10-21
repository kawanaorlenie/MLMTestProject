package org.mlm.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "org.mlm.service", "org.mlm.model",
		"org.mlm.dao" })
public class InfrastructureContextConfiguration {

	@Autowired
	private DataSource dataSourceTest;

	@Autowired
	private EntityManagerFactory entityManagerFactoryTest;

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactoryTest() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(this.dataSourceTest);
		emfb.setJpaVendorAdapter(jpaVendorAdapterTest());
		return emfb;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapterTest() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public PlatformTransactionManager transactionManagerTest() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(this.entityManagerFactoryTest);
		txManager.setDataSource(this.dataSourceTest);
		return txManager;
	}

	@Bean()
	public DataSource dataSourceTest() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
		return builder.build();
	}
}