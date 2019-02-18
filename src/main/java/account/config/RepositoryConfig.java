package account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"account.repository"})  // scan specific package(s) for repository interfaces
public class RepositoryConfig {
}
