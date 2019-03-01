package org.greta;

import java.util.stream.Stream;

import org.greta.dao.TacheRepository;
import org.greta.entities.AppRole;
import org.greta.entities.AppUser;
import org.greta.entities.Tache;
import org.greta.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner {
	
	@Autowired
	private TacheRepository tacheRepository;
	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		accountService.addUser(new AppUser("admin","1234",null));
		accountService.addUser(new AppUser("user","1234",null));
		accountService.addRole(new AppRole("ADMIN"));
		accountService.addRole(new AppRole("USER"));
		accountService.addRoleToUser("admin","ADMIN");
		accountService.addRoleToUser("admin","USER");
		accountService.addRoleToUser("user","USER");
		
		
		
		Stream.of("T1","T2","T3").forEach(t->{
			
			tacheRepository.save(new Tache(t));
			
		});
		
		tacheRepository.findAll().forEach(t->{
			
			System.out.println("nom de Tache :"+t.getNomTache());
		});
		
		
		
		
	}

}
