package io.github.ashayking.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Ashay
 *
 */
@Component
public class PBKDF2Encoder implements PasswordEncoder {

	private static final String PBKDF2_WITH_HMAC_SHA512_ALG = "PBKDF2WithHmacSHA512";

	@Value("${springboot-jwt.password.encoder.secret}")
	private String secret;

	@Value("${springboot-jwt.password.encoder.iteration}")
	private Integer iteration;

	@Value("${springboot-jwt.password.encoder.keylength}")
	private Integer keyLength;

	/**
	 * rawPassword : input password
	 * return : encoded password
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			byte[] result = SecretKeyFactory
							.getInstance(PBKDF2_WITH_HMAC_SHA512_ALG)
							.generateSecret(
										new PBEKeySpec(rawPassword.toString().toCharArray(), 
										secret.getBytes(), 
										iteration, 
										keyLength
										)
							)
							.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

}