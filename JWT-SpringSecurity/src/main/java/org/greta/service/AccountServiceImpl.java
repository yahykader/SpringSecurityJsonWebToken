package org.greta.service;

import org.greta.dao.RoleRepository;
import org.greta.dao.UserRepository;
import org.greta.entities.AppRole;
import org.greta.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AppUser addUser(AppUser user) {
		String HahPassword=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(HahPassword);
		return userRepository.save(user);
	}

	@Override
	public AppRole addRole(AppRole role) {
		
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {
		AppRole role=roleRepository.findByRoleName(roleName);
		AppUser user=userRepository.findByUserName(userName);
		user.getRoles().add(role);
		
	}

	@Override
	public AppUser findUserByUserName(String userName) {
	 
		return userRepository.findByUserName(userName);
	}

}
