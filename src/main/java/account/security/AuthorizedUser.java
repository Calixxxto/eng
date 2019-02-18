package account.security;

import data.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class AuthorizedUser extends User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3663810624317856276L;
	
	public AuthorizedUser() {
		super();
	}

	public AuthorizedUser(User user) {
		if (user != null) {
			this.setId(user.getId());
			this.setLogin(user.getLogin());
			this.setFullName(user.getFullName());
			this.setPassword(user.getPassword());
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}


	@Override
	public String getUsername() {
		return super.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
