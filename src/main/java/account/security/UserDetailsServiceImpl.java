package account.security;

import account.service.UserService;
import data.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Lazy
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user =  userService.findUserByLogin(userName);
		if (user == null) {
			throw new UsernameNotFoundException("UserName " + userName
					+ " not found");
		}
		return new AuthorizedUser(user);
	}

}
