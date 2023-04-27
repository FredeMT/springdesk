package br.com.springdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.springdesk.service.TecnicoUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebConfigProject extends WebSecurityConfigurerAdapter {
	
	@Autowired
	TecnicoUserDetailsService tecnicoUserDetailsService;
	
	
	//Define as permissoes dos recursos acessados e o restante é bloqueado (.anyRequest().authenticated())
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/vendors/**").permitAll()
        .anyRequest().authenticated();

        http.formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/") //ao logar redireciona para /
        .permitAll();

        http.logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher("/logout", "GET")
        )
        .logoutSuccessUrl("/login");
        
    }
	
	
	//Este que faz a autenticacao da api, quando o usuario passar o email e senha
	// ele vai encriptar e comparar no banco para fazer a autenticacao.
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(tecnicoUserDetailsService) //busca o usuario e senha no banco
        .passwordEncoder(new BCryptPasswordEncoder()); //encoda a senha.
    }

}
