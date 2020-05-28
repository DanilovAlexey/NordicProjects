package com.example.news.authenication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.news.config.UsersStorage;
import com.example.news.models.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UsersStorage usersStorage;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		ArrayList<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

		String userLogin = authentication.getName();
		String userPassword = authentication.getCredentials().toString();

		//System.out.println("CustomAuthenticationProvider " + userLogin);
		//System.out.println("CustomAuthenticationProvider " + userPassword);

	
		if (!usersStorage.checkUserAuth(userLogin, userPassword)) {
			throw new UsernameNotFoundException("1001");
		}


		roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new UsernamePasswordAuthenticationToken(userLogin, userLogin, roles);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.equals(UsernamePasswordAuthenticationToken.class);
	}

}
