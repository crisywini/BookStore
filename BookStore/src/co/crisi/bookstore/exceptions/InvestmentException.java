package co.crisi.bookstore.exceptions;

import java.io.Serializable;

public class InvestmentException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvestmentException(String errorMessage) {
		super(errorMessage);
	}

}
