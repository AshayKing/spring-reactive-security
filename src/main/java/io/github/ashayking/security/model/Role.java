package io.github.ashayking.security.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 
 * @author Ashay
 *
 */
public enum Role {
	ROLE_USER, ROLE_ADMIN;
	
	public static List<SimpleGrantedAuthority> toSimpleGrantedAuthorityList(List<Role> roles) {
		return roles.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toList());
	}
}