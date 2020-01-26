package co.crisi.bookstore.exceptions;

import java.io.Serializable;

public class EmptyLibraryException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyLibraryException(String errorMessage) {
		super(errorMessage);
	}

}
