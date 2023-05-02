package br.com.springdesk.model;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TecnicoUserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Tecnico tecnico;
	
	public TecnicoUserDetailsImpl(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
	
    public Integer getId(){
        return tecnico.getId();
    }

    public String getNome(){
        return tecnico.getNome();
    }

    public String displayImage(){
        return tecnico.getImagem();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return tecnico.getSenha();
	}

	@Override
	public String getUsername() {
		return tecnico.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// implementar metodo se a conta expirada.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// implementar metodo se a conta bloqueada
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// implementar metodo se as credenciais expiradas
		return true;
	}

	@Override
	public boolean isEnabled() {
		// implementar metodo se usuario ativo.
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tecnico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TecnicoUserDetailsImpl other = (TecnicoUserDetailsImpl) obj;
		return Objects.equals(tecnico, other.tecnico);
	}

}
