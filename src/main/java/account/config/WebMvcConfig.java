package account.config;


import commons.Const;
import commons.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "account.controller" })
public class WebMvcConfig extends SpringDataWebConfiguration {

	private static final Logger log = LoggerFactory.getLogger("error");

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		// addDefaultHttpMessageConverters(converters);
		// available only in WebMvcConfigurationSupport ,
		// as we are using SpringDataWebConfiguration that extends WebMvcConfigurerAdapter ,
		// add converters manually :
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(stringConverter);
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<Source>());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		converters.add(
				new MappingJackson2HttpMessageConverter(
						new Jackson2ObjectMapperBuilder()
								.failOnEmptyBeans(false) // KAP-802, KAP-803, KAP-804
								.defaultViewInclusion(true) //KAP-942
								.build()
								.setDateFormat(new JsonUtils.SafeDateFormat()) // KAP-1053
								.setTimeZone(Calendar.getInstance().getTimeZone())
				));


//		if (romePresent) {
//			messageConverters.add(new AtomFeedHttpMessageConverter());
//			messageConverters.add(new RssChannelHttpMessageConverter());
//		}
//		if (jaxb2Present) {
//			messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
//		}


	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/i18n/labels", "classpath:/i18n/messages", "classpath:/i18n/reference");
		messageSource.setCacheSeconds(0);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");
		return localeChangeInterceptor;
	}

	@Bean
	public SessionLocaleResolver sessionLocaleResolver(){
		SessionLocaleResolver localeResolver=new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("ru","RU"));
		return localeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(errorLoggingHandlerInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/WEB-INF/resources/")
				.setCachePeriod(60*60*24*365)
				.resourceChain(true)
				.addResolver(new GzipResourceResolver())
				.addResolver(new PathResourceResolver());
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);

		/**
		 * to make acceptable requests like DELETE http://localhost:8080/kap/api/doc/house/173952/file/cormen.pdf
		 * we ask SPRING not to check URL at first to obtain default content type
		 * http://stackoverflow.com/questions/22329393/springmvc-inconsistent-mapping-behavior-depending-on-url-extension
		 */
		configurer.favorPathExtension(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		// map views directly w/o controllers:
		registry.addViewController("login/form").setViewName("login");

	}

	@Bean
	public PropertyEditor datePropertyEditor() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.Json.Format.DATE_FORMAT);
		return new CustomDateEditor(simpleDateFormat, false);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

	@Bean
	public HandlerInterceptorAdapter errorLoggingHandlerInterceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
				if (ex != null) {
					log.error(ex.getMessage(), ex);
				}
			}
		};
	}

}
