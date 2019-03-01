package org.greta.security;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

	
	/* (non-Javadoc)
	 * @ apres l'authentification ,pour demande une ressource il faut lire le token créer au authentification 
	 * et quelle droit authorize a cet token 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-with,"
				+"Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers, Authorization");
		
		response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
		
		
		
		// verifier si l'objet request il  contient un header  
		String jwt=request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else {
		
				// si jwt est null et jwt ne commence pas par le token_prefix
				if(jwt== null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
					filterChain.doFilter(request, response);
					return;
				}
				//sinon je veux signe le Token
				Claims claims=Jwts.parser()
						           .setSigningKey(SecurityConstants.SECRET)
						           .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
						           .getBody();// recupere le contenu de token 
				String userName=claims.getSubject();// pour retourner le username
				//le roles sont stockes par cle,valeur ==> Map et tout en arrayList 
				ArrayList<Map<String,String>> roles = (ArrayList<Map<String,String>>) claims.get("roles");
				//spring va recuperer le roles sous forme de GrantedAuthority
				Collection<GrantedAuthority> authorities=new ArrayList<>();
				
				roles.forEach(r->{
					authorities.add(new SimpleGrantedAuthority(r.get("authority")));
			
				});
				//verifier le username,paswword,et le roles
				UsernamePasswordAuthenticationToken authenticationUser =
						new UsernamePasswordAuthenticationToken(userName, null,authorities);
				/* 
				 * acceder a  le contexte de securite de spring && charger l'utilisateur authentifie
				 */
				SecurityContextHolder.getContext()
				                     .setAuthentication(authenticationUser);
				
				
				filterChain.doFilter(request, response);
	}
	}
}
