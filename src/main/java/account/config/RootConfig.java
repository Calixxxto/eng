package account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({AopConfig.class, SecurityWebConfig.class, SecurityMethodConfig.class, ServiceConfig.class,
        RepositoryConfig.class, AsyncConfig.class})
public class RootConfig {

}
