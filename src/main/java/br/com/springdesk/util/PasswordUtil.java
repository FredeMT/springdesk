package br.com.springdesk.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
	
	public String encoder(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}

}
