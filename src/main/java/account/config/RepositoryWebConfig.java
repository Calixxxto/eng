package account.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import commons.Const;
import commons.JsonUtils;
import data.domain.user.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;

import java.util.Calendar;

@Configuration
public class RepositoryWebConfig extends RepositoryRestMvcConfiguration {
		 
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config
		 // include identity field in REST response for specified entities:
		.exposeIdsFor(User.class)
				.setMaxPageSize(Const.ResponseParam.LIMIT)
		.setReturnBodyOnCreate(true)
		.setReturnBodyOnUpdate(true);

		config.setDefaultMediaType(MediaType.APPLICATION_JSON); // disable HAL
		config.useHalAsDefaultJsonMediaType(false);
	}


	@Override protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		// KAP-803, KAP-804
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //KAP-942
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false); //needs to work correctly. In fact there are all fields in result.
        // KAP-1053
        objectMapper.setTimeZone(Calendar.getInstance().getTimeZone());
        objectMapper.setDateFormat(new JsonUtils.SafeDateFormat());

    }

}
