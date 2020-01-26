package co.crisi.bookstore.exceptions;

public class BookNullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BookNullException(String errorMessage) {
		super(errorMessage);
	}

}
