package org.greta.security;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity


public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService ;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	
	/*@Bean
	public PasswordEncoder passwordEncoder() {
	       return new BCryptPasswordEncoder();
	}
	*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*- 1- Authentification en memoire
		 auth 
	  		.inMemoryAuthentication()
	  		.withUser("user")
	  		.password(passwordEncoder().encode("1234"))
	  		.roles("USER") 
	  .and()
	  		.withUser("admin") 
	  		.password(passwordEncoder().encode("1234"))
	  		.roles("ADMIN","USER");
		 */
		 
		 /*-2- Authentification avec Jdbc
		 auth
			  .jdbcAuthentication()
			  .dataSource(datasource)
			  .usersByUsernameQuery("select username as principal ,password as credentials ,active from users where username=?")
			  .authoritiesByUsernameQuery("select username as principal ,role as role  from users_roles where username=?") 
			  .passwordEncoder(new Md4PasswordEncoder())
			  .rolePrefix("ROLE_"); 
			  */
		
		// -3- Authentification base sur UserDetailsService
		
		auth
		    .userDetailsService(userDetailsService)
		    .passwordEncoder(bCryptPasswordEncoder);
		 
	}
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable();
		 // deactiver l'authentification session et passe a JWT
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 // http.formLogin();
		 http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll();
		 http.authorizeRequests().antMatchers(HttpMethod.POST,"/taches/**").hasAuthority("ADMIN");
		 http.authorizeRequests().anyRequest().authenticated();
		 http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		 http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
