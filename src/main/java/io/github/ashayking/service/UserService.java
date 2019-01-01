package io.github.ashayking.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import io.github.ashayking.model.User;
import io.github.ashayking.security.model.Role;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Ashay
 *
 */
@Service
public class UserService {

	// user:user
	private final String userUsername = "user";
	private final User user = new User(userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true,
			Arrays.asList(Role.ROLE_USER));

	// admin:admin
	private final String adminUsername = "admin";
	private final User admin = new User(adminUsername, "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true,
			Arrays.asList(Role.ROLE_ADMIN));

	public Mono<User> findByUsername(String username) {
		if (username.equals(userUsername)) {
			return Mono.just(user);
		} else if (username.equals(adminUsername)) {
			return Mono.just(admin);
		} else {
			return Mono.empty();
		}
	}
}
