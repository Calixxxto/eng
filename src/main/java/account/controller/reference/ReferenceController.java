package account.controller.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ReferenceController {

	@Autowired 
	ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * Return full reference
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/api/reference"},  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> getReference(HttpServletRequest request) {
		Map<String, Object> reference = new HashMap<String, Object>();
		return reference;
	}

}
