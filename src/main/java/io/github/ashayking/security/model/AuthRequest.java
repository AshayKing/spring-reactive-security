package io.github.ashayking.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Ashay
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {
	private String username;
	private String password;
}