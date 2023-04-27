package br.com.springdesk.model;

public enum Perfil {
	
	ADMIN ("Administrador"),
	TECNICO ("Técnico"),
	CLIENTE ("Cliente");
	
	private final String descricao;
	
	private Perfil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
