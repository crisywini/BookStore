package co.crisi.bookstore.application;

import java.util.ArrayList;

import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.EmptyLibraryException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.exceptions.RepeatedBookException;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.util.Date;

public interface IBookStoreControl {
	public void addBook(String ISBN, String title, double purchasePrice, double salePrice, int quantity)
			throws RepeatedBookException, InvestmentException;

	public Book removeBook(String ISBN) throws BookNullException;

	public Book searchBookByTitle(String title) throws BookNullException;

	public Book searchBookByISBN(String ISBN) throws BookNullException;

	public void supplyBook(String ISBN, int quantity, Date date)
			throws BookNullException, InvestmentException, QuantityException;

	public void toSellBook(String ISBN, int quantity, Date date) throws BookNullException, QuantityException;

	public int getNumberOfTransactions(String ISBN) throws BookNullException;

	public Book getMostExpensiveBook() throws EmptyLibraryException;

	public Book getCheapestBook() throws EmptyLibraryException;

	public Book getBestSeller() throws EmptyLibraryException,BookNullException;
	public ArrayList<Book> getBooksInArrayList();

}
