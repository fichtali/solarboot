package emx.solar.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emx.solar.pack.entities.RoleEntity;
import emx.solar.pack.entities.UserEntity;
import emx.solar.pack.repositories.RoleEntityRepository;
import emx.solar.pack.repositories.UserEntityRepository;
import emx.solar.pack.service.IAccountService;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserEntityRepository userRepo;
	
	@Autowired
	private RoleEntityRepository roleRepo;

	@Override
	public UserEntity saveUser(UserEntity user) {
		String hashPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPass);
		return userRepo.save(user);
	}

	@Override
	public RoleEntity saveRole(RoleEntity role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		UserEntity u = userRepo.findByUsername(username);
		RoleEntity r = roleRepo.findByRoleName(rolename);
		u.getRoles().add(r);
	}

	@Override
	public UserEntity findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

}
