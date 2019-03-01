package org.greta.service;

import org.greta.entities.AppRole;
import org.greta.entities.AppUser;

public interface AccountService {
	public AppUser addUser(AppUser user);
	public AppRole addRole(AppRole role);
	public void addRoleToUser(String userName,String roleName);
	public AppUser findUserByUserName(String userName);

}
