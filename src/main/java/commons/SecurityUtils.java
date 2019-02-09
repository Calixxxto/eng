package commons;

import data.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
	
	public static User getLoggedinUser() {
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&				
				SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} else {
			return null ; 
		}
	}
}
