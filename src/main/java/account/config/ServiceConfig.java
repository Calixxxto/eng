package account.config;

import commons.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@PropertySource({"classpath:kap-account.properties"})
@ComponentScan(basePackages = {"account.service"})
@EnableScheduling
public class ServiceConfig implements SchedulingConfigurer {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    Executor executor;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(Const.Report.REPORT_SWAP_PATH));
    }

    /**
     * Need this to support @Value property resolution in beans not defined with @Bean
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
        return freeMarkerConfigurer;
    }


    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setCache(true);
        return freeMarkerViewResolver;
    }

    @Bean
    public freemarker.template.Configuration configuration() {
        freemarker.template.Configuration configuration = freemarkerConfig().getConfiguration();
        configuration.setLocale(new Locale("ru","RU"));
        configuration.setDateFormat(environment.getProperty("report.date.format"));
        configuration.setDateTimeFormat(environment.getProperty("report.date.time.format"));
        return configuration;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // TODO: try to set same executor for both ServiceConfig and AsyncConfig
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
