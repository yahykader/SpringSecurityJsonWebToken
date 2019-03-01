package org.greta.security;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.greta.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		AppUser appUser=null;
		
		/* si on voix le donnée de type: 3W URL Encode
		 * String userName=request.getParameter("userName"); String
		 * password=request.getParameter("password");
		 */
		
		try {
			
			/* on peut envoyer le donne de formulaire de type JSON
			 * new ObjectMapper():: permet de prendre de l'objet JSON et de stocker en objet
			 * JAVA readValue(prend le contenu de la requette,et de desrialize de type objet
			 * de type AppUser.class)
			 */
			
			appUser=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("************************************");
		System.out.println("user name :"+appUser.getUserName());
		System.out.println("password :"+appUser.getPassword());
		return authenticationManager
				 .authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User userSpring= (User) authResult.getPrincipal();
		String jwt=Jwts.builder()
				        .setSubject(userSpring.getUsername())
				        .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.Expiration_TIME))
				        .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
				        .claim("roles", userSpring.getAuthorities())
				        .compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);
		
	}

}
