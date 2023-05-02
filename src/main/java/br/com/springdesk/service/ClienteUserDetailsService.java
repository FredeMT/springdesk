package br.com.springdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springdesk.model.Cliente;
import br.com.springdesk.model.ClienteUserDetailsImpl;
import br.com.springdesk.repository.ClienteRepository;


@Service
public class ClienteUserDetailsService implements UserDetailsService{
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente cliente = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
		return new ClienteUserDetailsImpl(cliente);
	}

}
