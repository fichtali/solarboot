package emx.solar.pack.service;

import emx.solar.pack.domains.RoleEntity;
import emx.solar.pack.domains.UserEntity;

public interface IAccountService {
	
	public UserEntity saveUser(UserEntity user);
	
	public RoleEntity saveRole(RoleEntity user);
	
	public void addRoleToUser(String username, String rolename);
	
	public UserEntity findUserByUsername(String username);
	
}
