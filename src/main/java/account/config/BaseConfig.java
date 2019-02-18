package account.config;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springmodules.commons.configuration.CommonsConfigurationFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(JpaConfig.class)
public class BaseConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void addDatabaseConfigurationSource() throws Exception {
        CommonsConfigurationFactoryBean commonsConfigurationFactoryBean = new CommonsConfigurationFactoryBean(
                new DatabaseConfiguration(dataSource, "kap_config", "key", "value"));
        PropertiesPropertySource propertySource = new PropertiesPropertySource("kap_database_config",
                (Properties) commonsConfigurationFactoryBean.getObject());
        environment.getPropertySources().addFirst(propertySource);
    }
}
