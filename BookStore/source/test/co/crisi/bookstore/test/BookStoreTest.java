package co.crisi.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.EmptyLibraryException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.exceptions.RepeatedBookException;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.model.BookStore;
import co.crisi.bookstore.util.Date;

/**
 * This class is created to test the BookStore methods
 * 
 * @author Crisi Sánchez Pineda
 *
 */
public class BookStoreTest {

	private BookStore bookStore;
	private Date date;

	private void setUpStage1() {
		bookStore = new BookStore();
		date = new Date();
	}

	private void setUpStage2() throws RepeatedBookException, InvestmentException {
		bookStore = new BookStore(2000000);
		date = new Date();
		bookStore.addBook("123456", "Ella mi sueño y el mar", 20000, 25500, 15);
		bookStore.addBook("1234567", "Brevisima historia del tiempo", 20000, 25000, 31);
	}

	@Test
	public void testIsBookOnStore() throws RepeatedBookException, InvestmentException {
		setUpStage2();
		assertTrue("No encontró de manera correcta", bookStore.isBookOnStore("123456"));
	}

	@Test
	public void testAddBook() throws RepeatedBookException, InvestmentException {
		setUpStage1();
		bookStore.addBook("123456", "Como programar en Java", 145000, 200000, 4);
		bookStore.addBook("1234567", "Como programar en Python", 120000, 130000, 5);
		assertTrue("No se añadieron los libros de manera correcta", bookStore.getMyBooks().size() == 2);
	}

	@Test
	public void testRemoveBook() throws RepeatedBookException, InvestmentException, BookNullException {
		setUpStage2();
		Book removed = bookStore.removeBook("123456");
		Book removed2 = bookStore.removeBook("1234567");
		assertFalse("No se elimino de manera correcta", (removed == null || removed2 == null));
	}

	@Test
	public void testSearchBookByTitle() throws RepeatedBookException, InvestmentException, BookNullException {
		setUpStage2();
		Book searched1 = bookStore.searchBookByTitle("ella mi sueño y el mar");
		Book searched2 = bookStore.searchBookByTitle("Brevisima historia del tiempo");
		assertFalse("No se buscó de manera correcta", (searched1 == null || searched2 == null));
	}

	@Test
	public void testSearchBookByISBN() throws RepeatedBookException, InvestmentException, BookNullException {
		setUpStage2();
		Book searched1 = bookStore.searchBookByISBN("123456");
		Book searched2 = bookStore.searchBookByISBN("1234567");
		assertFalse("No se buscó de manera correcta", (searched1 == null || searched2 == null));
	}

	@Test
	public void testSupplyBook()
			throws RepeatedBookException, InvestmentException, BookNullException, QuantityException {
		setUpStage2();
		bookStore.supplyBook("123456", 2, date);
		Book book = bookStore.searchBookByISBN("123456");
		assertTrue("No se abasteció de manera correcta", book.getQuantity() == 17);
	}

	@Test
	public void testToSellBook()
			throws RepeatedBookException, InvestmentException, BookNullException, QuantityException {
		setUpStage2();// Investment = 1080000
		bookStore.toSellBook("1234567", 30, date);// 750000+investment=1830000
		bookStore.toSellBook("123456", 10, date);// 255000+investment=2085000
		double investmentBookStore = bookStore.getInvestment();
		Book one = bookStore.searchBookByISBN("1234567");
		Book two = bookStore.searchBookByISBN("123456");
		assertTrue("No se vendió de manera correcta",
				(investmentBookStore == 2085000) && one.getQuantity() == 1 && two.getQuantity() == 5);
	}

	@Test
	public void testGetNumberOfTransactions()
			throws BookNullException, RepeatedBookException, InvestmentException, QuantityException {
		setUpStage2();
		Book one = bookStore.searchBookByISBN("123456");
		Book two = bookStore.searchBookByISBN("1234567");
		bookStore.supplyBook("1234567", 4, date);
		bookStore.supplyBook("123456", 5, date);
		bookStore.toSellBook("1234567", 10, date);
		bookStore.toSellBook("123456", 14, date);
		assertTrue("No se obtuvo el numero de transacciones correcto",
				(one.getMyTransactions().size() == 2) && (two.getMyTransactions().size() == 2));
	}

	@Test
	public void testGetMostExpensiveBook()
			throws RepeatedBookException, InvestmentException, EmptyLibraryException, BookNullException {
		setUpStage2();
		Book mostExpensive = bookStore.getMostExpensiveBook();
		Book one = bookStore.searchBookByISBN("123456");
		assertSame(one, mostExpensive);
	}

	@Test
	public void testGetCheapestBook()
			throws RepeatedBookException, InvestmentException, EmptyLibraryException, BookNullException {
		setUpStage2();
		Book two = bookStore.getCheapestBook();
		Book two2 = bookStore.searchBookByISBN("1234567");
		assertSame(two2, two);
	}

	@Test
	public void testGetBestSeller() throws BookNullException, QuantityException, RepeatedBookException,
			InvestmentException, EmptyLibraryException {
		setUpStage2();
		Book two = bookStore.searchBookByISBN("1234567");
		bookStore.toSellBook("123456", 14, date);
		bookStore.toSellBook("1234567", 30, date);
		Book bestSeller = bookStore.getBestSeller();
		assertSame(two, bestSeller);
	}

}
