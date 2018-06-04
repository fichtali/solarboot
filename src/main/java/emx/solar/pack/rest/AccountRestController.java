package emx.solar.pack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emx.solar.pack.domains.UserEntity;
import emx.solar.pack.dto.RegisterForm;
import emx.solar.pack.service.IAccountService;

@RestController
public class AccountRestController {

	@Autowired
	private IAccountService accountService;
	
	@PostMapping("/register")
	public UserEntity register(@RequestBody RegisterForm user) {
		
		/*if(!user.getPassword().equals(user.getRepassword())) throw new RuntimeException("Password not same!!");
		AppUser u = accountService.findUserByUsername(user.getUsername());
		if(u != null) throw new RuntimeException("User already exist !!");
		
		AppUser nouv = new AppUser();
		nouv.setUsername(user.getUsername());
		nouv.setPassword(user.getPassword());
		accountService.saveUser(nouv);
		
		accountService.addRoleToUser(user.getUsername(), "USER");*/
		return null;
	}
	
	
	
}
