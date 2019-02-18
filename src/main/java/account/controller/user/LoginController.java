package account.controller.user;

import account.security.AuthorizedUser;
import commons.SecurityUtils;
import data.domain.user.User;
import data.domain.user.UserName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	
	static final Logger log = LoggerFactory.getLogger("login");
	
	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@RequestMapping(value={"/api/login"},  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object login(@RequestBody User user, HttpServletResponse response) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user.getLogin(), user.getPassword());
		AuthorizedUser userDetails = new AuthorizedUser();
		userDetails.setLogin(user.getLogin());
		token.setDetails(userDetails);

		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			logAuthEvent("User %s logged in succesfully", user.getLogin());
			return auth.getPrincipal();
		} catch (BadCredentialsException e) {
			logAuthEvent("User %s login attempt failed - bad credentials", user.getLogin());
			return "";
		} catch (DisabledException e) {
            logAuthEvent("User %s login attempt failed - user is disabled", user.getLogin());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "";
        }

	}

	@RequestMapping(value={"/api/logout"},  method = RequestMethod.POST)
	@ResponseBody
	public String logout(HttpServletResponse response) {
		logAuthEvent("User %s is logging out", 
				SecurityUtils.getLoggedinUser() == null ? "UNKNOWN" : SecurityUtils.getLoggedinUser().getLogin());
		SecurityContextHolder.clearContext();
		response.setStatus(HttpServletResponse.SC_OK);
		return "";
	}

	@RequestMapping(value={"/api/loggedin"},  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object loggedIn(HttpServletResponse response) {
		User user = SecurityUtils.getLoggedinUser(); 		
		if (user != null) {
			return user;
		} else {
			return "";
		}
	}
	
	private void logAuthEvent(String message, String userName) {
		if (!UserName.SITEUSER.getValue().equalsIgnoreCase(userName)) {
			log.info(String.format(message, userName)) ;
		}
	}
	
	
}
