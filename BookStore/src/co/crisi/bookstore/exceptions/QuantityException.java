package co.crisi.bookstore.exceptions;

import java.io.Serializable;

public class QuantityException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuantityException(String errorMessage) {
		super(errorMessage);
	}

}
