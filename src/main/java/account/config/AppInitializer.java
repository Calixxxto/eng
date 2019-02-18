package account.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


public class AppInitializer implements WebApplicationInitializer {
    static final Logger log = LoggerFactory
			.getLogger(WebApplicationInitializer.class);
    private static final int FILE_MAX_SIZE = 500;
    private static final int FILE_MAX_REQUEST_SIZE = 500;

    @Override
	public void onStartup(ServletContext servletContext) {

		log.info("Initializing KAP accounting system");

		// print logback's internal status:
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);

        AnnotationConfigWebApplicationContext rootContext = createContext(
                "account.config.BaseConfig",
                "account.config.RootConfig");

		// MVC:
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
						"DispatcherServlet",
						new DispatcherServlet(createContext("account.config.WebMvcConfig")));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
        int fileMaxSize = getIntPropertyValue(rootContext, "upload.file.maxSize", FILE_MAX_SIZE);
        int fileMaxRequestSize = getIntPropertyValue(rootContext, "upload.file.maxRequestSize", FILE_MAX_REQUEST_SIZE);
        dispatcher.setMultipartConfig(new MultipartConfigElement("/", 1024 * 1024 * fileMaxSize, 1024 * 1024 * fileMaxRequestSize, 1024 * 1024));

		// REST:
		ServletRegistration.Dynamic rest = servletContext.addServlet(
						"RestServlet",
						new DispatcherServlet(createContext("account.config.RepositoryWebConfig")));
		rest.setLoadOnStartup(2);
		rest.addMapping("/repository/*");

		// common root context:
		servletContext.addListener(new ContextLoaderListener(rootContext));

		// encoding: 
		FilterRegistration.Dynamic characterEncodingFilterReg = 
				servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		characterEncodingFilterReg.setInitParameter("encoding", "UTF-8");
		characterEncodingFilterReg.setInitParameter("forceEncoding", "true");
		characterEncodingFilterReg.addMappingForUrlPatterns(null, false, "/*");

		// entity manager (session) in view:
		FilterRegistration.Dynamic openEntityManagerInViewFilterReg = 
				servletContext.addFilter("openEntityManagerInViewFilter", new OpenEntityManagerInViewFilter());
		openEntityManagerInViewFilterReg.addMappingForServletNames(null, true, "DispatcherServlet");

	}

    private int getIntPropertyValue(AnnotationConfigWebApplicationContext rootContext, String propertyKey, int defaultValue) {
        String value = rootContext.getEnvironment().getProperty(propertyKey);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    private AnnotationConfigWebApplicationContext createContext(String configClass) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(configClass);
		return context;
	}

    private AnnotationConfigWebApplicationContext createContext(String configClass, String configClass2) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocations(new String [] {configClass, configClass2});
        return context;
    }
}