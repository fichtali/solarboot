package emx.solar.pack.entities;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data 
public class AuthUser implements UserDetails {

	private static final long serialVersionUID = -3274992034108013859L;
	private UserEntity user; 

	@Override
	public Collection<RoleEntity> getAuthorities() {
		return user.getRoles();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
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

}
