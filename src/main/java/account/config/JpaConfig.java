package account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:kap-data.properties")
public class JpaConfig {
	
    @Autowired
    private Environment env;
    
    @Bean
    public AbstractPlatformTransactionManager transactionManager(EntityManagerFactory emf) {    	
    	return new JpaTransactionManager(emf);    	
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setDataSource(dataSource());
        final Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql","true"));
        props.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql","true"));
        props.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size","512"));
        props.setProperty("hibernate.jdbc.batch_versioned_data", env.getProperty("hibernate.jdbc.batch_versioned_data","true"));             
        props.setProperty("cache.use_query_cache", env.getProperty("cache.use_query_cache","false"));
        props.setProperty("cache.use_second_level_cache", env.getProperty("cache.use_second_level_cache","false"));
        props.setProperty("hibernate.order_updates", env.getProperty("hibernate.order_updates","true"));
        props.setProperty("hibernate.order_inserts", env.getProperty("hibernate.order_inserts","true"));
        bean.setJpaProperties(props);
        bean.setPackagesToScan(new String[] { "data.domain" });
        bean.setMappingResources("orm.xml");
        return bean;
    }

    @Bean
	public DataSource dataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		return dsLookup.getDataSource(env.getProperty("datasource.jndi.name")); // JNDI datasource should be defined in web-server context
	}
	 
}
