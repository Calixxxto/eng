package account.config;


import account.aop.RepositoryAuditedRecordAspect;
import account.aop.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@ComponentScan(basePackages = "account.aop")
public class AopConfig {

    @Bean
    public RepositoryAuditedRecordAspect auditedRecordAspect() {
        return new RepositoryAuditedRecordAspect();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
