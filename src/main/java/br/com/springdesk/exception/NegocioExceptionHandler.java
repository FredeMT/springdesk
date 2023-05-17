package br.com.springdesk.exception;

public class NegocioExceptionHandler extends RuntimeException {

	/**
	 * Trata as exceptions das regras de negócio.
	 */
	private static final long serialVersionUID = 1L;
	
	public NegocioExceptionHandler (String message) {
		super(message);
	}

}
