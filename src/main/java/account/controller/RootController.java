package account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;


@Controller
public class RootController {
	static final Logger log = LoggerFactory.getLogger(RootController.class) ; 

	private Map<String,String> manifestAttributes ;
	
	@RequestMapping("/")
    public ModelAndView processRequest(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.getModel().put(commons.Const.Json.MANIFEST, getManifestAttributes(request));
		return modelAndView;
    }
	
	@RequestMapping("/api/root/appinfo")
	@ResponseBody
    public Map<String,Map<String,String>> getAppInfo(HttpServletRequest request) {		
		Map<String,Map<String,String>> response = new HashMap<String,Map<String,String>>();
		response.put(commons.Const.Json.MANIFEST, getManifestAttributes(request));
        return response;
    }
	
	private Map<String,String> getManifestAttributes(HttpServletRequest request)  {
		if (manifestAttributes == null) {
			manifestAttributes = new HashMap<String,String>();
			InputStream input = request.getSession().getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
			Manifest mf = new Manifest();
			try {
				mf.read(input);
				for (Object key: mf.getMainAttributes().keySet() ) {
					manifestAttributes.put(((Name)key).toString(), mf.getMainAttributes().get(key).toString());
				}		
			} catch (IOException e) {
				log.error("Error reading app.manifest",e);
			}

		}
		return manifestAttributes; 
	}
	
	
}
