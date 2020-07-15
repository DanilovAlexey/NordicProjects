package com.example.storage.authentication;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.storage.model.User;
import com.example.storage.service.UserServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		ArrayList<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

		String userEmail = authentication.getName();
		String userPassword = authentication.getCredentials().toString();
		
		
		
		User user = userServiceImpl.getUser(userEmail);
        if(user == null) {
            throw new UsernameNotFoundException("1001");
        }
        
       
        
        if (!bcryptPasswordEncoder.matches(userPassword, user.getUserPassword().trim())) {
            throw new BadCredentialsException(userEmail);
        }
        
		
		
		roles.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().trim()));

		return new UsernamePasswordAuthenticationToken(userEmail, userPassword, roles);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.equals(UsernamePasswordAuthenticationToken.class);

	}

}