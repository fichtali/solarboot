package emx.solar.pack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import emx.solar.pack.domains.AuthUser;
import emx.solar.pack.domains.UserEntity;
import emx.solar.pack.service.IAccountService;

@Service("CustomUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IAccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity u = accountService.findUserByUsername(username);

		if (u == null)
			throw new UsernameNotFoundException(username);

		List<GrantedAuthority> authorities = new ArrayList<>();

		u.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		AuthUser authUser = new AuthUser();
		authUser.setUser(u);
		return authUser;
	}	

}
