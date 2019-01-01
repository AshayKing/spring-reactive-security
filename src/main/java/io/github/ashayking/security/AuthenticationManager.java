package io.github.ashayking.security;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.github.ashayking.security.model.Role;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * 
 * @author ashay
 *
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {

		String authToken = authentication.getCredentials().toString();

		String username;
		try {
			username = jwtUtil.getUsernameFromToken(authToken);
		} catch (Exception e) {
			username = null;
		}

		// Invalid UserName or Invalid Token
		if (Objects.isNull(username) || !jwtUtil.validateToken(authToken)) {
			return Mono.empty();
		}

		Claims claims = jwtUtil.getAllClaimsFromToken(authToken);

		// TODO: Refactor
		List<String> rolesMap = claims.get("role", List.class);
		List<Role> roles = rolesMap.stream().map(Role::valueOf).collect(Collectors.toList());
		return Mono.just(new UsernamePasswordAuthenticationToken(username, null, Role.toSimpleGrantedAuthorityList(roles)));
	}

}