package emx.solar.pack.service;

import emx.solar.pack.entities.RoleEntity;
import emx.solar.pack.entities.UserEntity;

public interface IAccountService {
	
	public UserEntity saveUser(UserEntity user);
	
	public RoleEntity saveRole(RoleEntity user);
	
	public void addRoleToUser(String username, String rolename);
	
	public UserEntity findUserByUsername(String username);
	
}
