package br.com.springdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.springdesk.exception.AccessDeniedExceptionHandler;
import br.com.springdesk.exception.CustomAuthenticationFailureHandler;
import br.com.springdesk.service.ClienteUserDetailsService;
import br.com.springdesk.service.TecnicoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigProject {
	
	@Autowired
	TecnicoUserDetailsService tecnicoUserDetailsService;
	
	@Autowired
	ClienteUserDetailsService clienteUserDetailsService;
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//Define as permissoes dos recursos acessados e o restante é bloqueado (.anyRequest().authenticated())
				http.authorizeRequests()
		        .antMatchers("/images/**").permitAll()
		        .antMatchers("/css/**").permitAll()
		        .antMatchers("/js/**").permitAll()
		        .antMatchers("/fonts/**").permitAll()
		        .antMatchers("/vendors/**").permitAll()
		        .antMatchers("/recursos/403").permitAll()
		        .antMatchers("/login")
		        .anonymous()
		        .antMatchers("/login-error")
		        .anonymous()
		        .anyRequest().authenticated()
		        .and()
		        .exceptionHandling()
		        .accessDeniedHandler(accessDeniedHandler())
		        .and()
		        .formLogin()
		        .loginPage("/login")
		        .defaultSuccessUrl("/") //ao logar redireciona para /
		        .permitAll()
		        .failureHandler(authenticationFailureHandler())
		        .and()
		      //Usar get para realizar logout e se sair com sucesso redireciona para /login.
		        .logout(logout -> logout
		        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")))
		        .sessionManagement(session -> session
		        		.maximumSessions(1)
		        		.maxSessionsPreventsLogin(true)
		        		.and()
			            .invalidSessionUrl("/login"));
	    return http.build();
	}
	
	
	
	
	//Este que faz a autenticacao da api, quando o usuario passar o email e senha
	// ele vai encriptar e comparar no banco para fazer a autenticacao.
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(tecnicoUserDetailsService)
	      .passwordEncoder(new BCryptPasswordEncoder())
	      .and()
	      .userDetailsService(clienteUserDetailsService)
	      .passwordEncoder(new BCryptPasswordEncoder())
	      .and()
	      .parentAuthenticationManager(null)
	      .build();
	}
	
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedExceptionHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
	    return new CustomAuthenticationFailureHandler();
	}

}