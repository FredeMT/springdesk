package br.com.springdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.springdesk.service.ClienteUserDetailsService;
import br.com.springdesk.service.TecnicoUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebConfigProject extends WebSecurityConfigurerAdapter {
	
	@Autowired
	TecnicoUserDetailsService tecnicoUserDetailsService;
	
	@Autowired
	ClienteUserDetailsService clienteUserDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Define as permissoes dos recursos acessados e o restante é bloqueado (.anyRequest().authenticated())
		http.authorizeRequests()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/vendors/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/") //ao logar redireciona para /
        .permitAll()
        .and()
      //Usar get para realizar logout e se sair com sucesso redireciona para /login.
        .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher("/logout", "GET")
        )
        .and()
        .sessionManagement()
        .invalidSessionUrl("/login");
	}
	
	
	
	
	//Este que faz a autenticacao da api, quando o usuario passar o email e senha
	// ele vai encriptar e comparar no banco para fazer a autenticacao.
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(tecnicoUserDetailsService) //busca o usuario e senha no banco
        .passwordEncoder(new BCryptPasswordEncoder()); //encoda a senha.
        auth.userDetailsService(clienteUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

}
