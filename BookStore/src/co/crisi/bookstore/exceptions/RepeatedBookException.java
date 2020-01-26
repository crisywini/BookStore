package co.crisi.bookstore.exceptions;

import java.io.Serializable;

public class RepeatedBookException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepeatedBookException(String errorMessage) {
		super(errorMessage);
	}

}
