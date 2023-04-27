package br.com.springdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springdesk.model.Tecnico;
import br.com.springdesk.model.TecnicoUserDetailsImpl;
import br.com.springdesk.repository.TecnicoRepository;

@Service
public class TecnicoUserDetailsService implements UserDetailsService {
	
	@Autowired
	TecnicoRepository tecnicoRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Tecnico tecnico = tecnicoRepository.findFirstByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
		return new TecnicoUserDetailsImpl(tecnico);
	}

}
