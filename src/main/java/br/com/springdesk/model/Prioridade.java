package br.com.springdesk.model;

public enum Prioridade {
	ALTA("Alta"),
	MEDIA("Média"),
	BAIXA("Baixa");
	
	private String descricao;
	
	private Prioridade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
