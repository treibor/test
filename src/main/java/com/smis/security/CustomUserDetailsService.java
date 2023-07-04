package com.smis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smis.dbservice.Dbservice;
import com.smis.entity.Users;
import com.smis.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	Dbservice service;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//System.out.println("Username:"+username);
		Users user= service.findUser(username);
		//System.out.println("User Object:"+user);
		if(user==null) {
			throw new UsernameNotFoundException(username +"not found.");
		}
		return new CustomUserDetails(user);
	}

}
