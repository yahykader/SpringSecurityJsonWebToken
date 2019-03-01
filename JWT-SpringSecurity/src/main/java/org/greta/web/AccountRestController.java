package org.greta.web;

import org.greta.entities.AppUser;
import org.greta.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
	@Autowired
	private AccountService accountService;
	
	
	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm  registerForm ) {
		/**
		 * @param registerForm
		 * @return ajouter un utilisateur ,et verifier si userName exist . verifier si password et repassword sont identique
		 */
		
		if(!registerForm.getPassword().equals(registerForm.getRepassword()))
								throw new RuntimeException("Confirmez votre password svp ");
		
		AppUser user=accountService.findUserByUserName(registerForm.getUserName());
		if(user!=null)
		                        throw new RuntimeException("Nom d'utilisateur existe déja ");  
		
		AppUser appUser=new AppUser();
		appUser.setUserName(registerForm.getUserName());
		appUser.setPassword(registerForm.getPassword());
		accountService.addUser(appUser);
		accountService.addRoleToUser(registerForm.getUserName(), "USER");
		return appUser;
	} 

}
