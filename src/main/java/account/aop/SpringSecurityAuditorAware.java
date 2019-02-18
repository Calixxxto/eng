package account.aop;


import commons.SecurityUtils;
import data.domain.user.User;
import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	  public String getCurrentAuditor() {
	    User user = SecurityUtils.getLoggedinUser();
	    return user == null ? null : user.getLogin();
	}
}