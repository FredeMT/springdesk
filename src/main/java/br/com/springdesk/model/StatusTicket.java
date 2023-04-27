package br.com.springdesk.model;

public enum StatusTicket {
	
	ABERTO ("Aberto"),
	ANDAMENTO("Em Andamento"),
	FECHADO("Fechado");
	
	private final String chamadoTicket;
	
	private StatusTicket(String chamadoTicket) {
		this.chamadoTicket = chamadoTicket;
	}

	public String getChamadoTicket() {
		return chamadoTicket;
	}

}
