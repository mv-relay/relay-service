package org.landcycle.dao;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.H2Dialect;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@Component("repositoryConfig")
@Configuration
public class RepositoryConfig {

//	@Autowired
//	@Qualifier("dbDataSource")
//	private DataSource dataSource;
//
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
//
//	@Bean
//	public SessionFactory sessionFactory() throws Exception {
//		System.out.println("SessionFactory");
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean() {
//			public SessionFactory getObject() {
//				System.out.println("getSession");
//				return super.getObject();
//			}
//		};
//
//		SessionFactory session = sessionFactoryBean.getObject();
//		// sessionFactoryBean.afterPropertiesSet();
//
//		return session;
//	}
//
//	@Bean
//	public EntityManagerFactory entityManagerFactory() {
//		System.out.println("Entity Manager");
//		JpaVendorAdapter vendorAdapter = jpaVendorAdapter();
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//
//		Properties jpaProperties = new Properties();
//		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
//
//		factory.setJpaProperties(jpaProperties);
//		factory.setJpaVendorAdapter(vendorAdapter);
//		factory.setPackagesToScan("*");
//		factory.setDataSource(dataSource);
//
//		factory.afterPropertiesSet();
//
//		return factory.getObject();
//	}
//
//	@Bean
//	public JpaVendorAdapter jpaVendorAdapter() {
//		System.out.println("jpaVendorAdapter");
//		final HibernateJpaVendorAdapter rv = new HibernateJpaVendorAdapter();
//
//		rv.setDatabase(Database.MYSQL);
//		rv.setDatabasePlatform(H2Dialect.class.getName());
//		rv.setGenerateDdl(false);
//		rv.setShowSql(true);
//
//		return rv;
//	}
//
//	static public class DataSou extends AbstractRoutingDataSource {
//		private final ThreadLocal<String> holder = new ThreadLocal<String>();
//
//		@Override
//		protected Object determineCurrentLookupKey() {
//			return holder.get();
//		}
//
//		public void clear() {
//			holder.remove();
//		}
//
//		public void setDataSourceKey(String key) {
//			holder.set(key);
//		}
//	};

}
