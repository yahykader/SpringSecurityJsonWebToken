package org.greta.dao;

import org.greta.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	public AppUser findByUserName(String userName);

}
