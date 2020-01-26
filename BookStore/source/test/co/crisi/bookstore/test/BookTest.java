package co.crisi.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.util.Date;
/**
 * This class is created to do test 
 * @author Crisi Sánchez Pineda
 *
 */
public class BookTest {

	private Book book;
	private Date date;

	/**
	 * Creates a new book and a new date without books
	 */
	private void setUpStage1() {
		book = new Book("123456", "Ella mi sueño y el mar", 20000, 25000, 0);
		date = new Date();
	}

	private void setUpStage2() {
		book = new Book("123456", "Ella mi sueño y el mar", 20000, 25000, 200);
		date = new Date();
	}

	@Test
	public void testSupply() throws QuantityException {
		setUpStage1();
		book.supply(100, date);
		book.supply(45, date);
		book.supply(1, date);
		assertTrue("No se abastecio correctamente", book.getQuantity() == 146);
	}

	@Test
	public void testToSell() throws QuantityException {
		setUpStage2();
		book.toSell(150, date);
		book.toSell(1, date);
		assertTrue("No se vendio correctament", book.getQuantity() == 49);
	}

	@Test
	public void testTransactions() throws QuantityException {
		setUpStage2();
		assertTrue("Numero de transacciones incorrecto", book.getMyTransactions().size() == 0);
		book.toSell(1, date);
		book.supply(1, date);
		assertTrue("Numero de transacciones incorrecto", book.getMyTransactions().size() == 2);
	}
}
