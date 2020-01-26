package co.crisi.bookstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.EmptyLibraryException;
import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.exceptions.RepeatedBookException;
import co.crisi.bookstore.util.Date;

/**
 * This class is the main class
 * 
 * @author Crisi Sánchez Pineda
 *
 */
public class BookStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeMap<String, Book> myBooks;
	private double investment;

	public BookStore() {
		this(1000000);
	}

	public BookStore(double investment) {
		this.investment = investment;
		myBooks = new TreeMap<String, Book>();
	}

	public TreeMap<String, Book> getMyBooks() {
		return myBooks;
	}

	public void setMyBooks(TreeMap<String, Book> myBooks) {
		this.myBooks = myBooks;
	}

	public double getInvestment() {
		return investment;
	}

	public void setInvestment(double investment) {
		this.investment = investment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(investment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((myBooks == null) ? 0 : myBooks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookStore other = (BookStore) obj;
		if (Double.doubleToLongBits(investment) != Double.doubleToLongBits(other.investment))
			return false;
		if (myBooks == null) {
			if (other.myBooks != null)
				return false;
		} else if (!myBooks.equals(other.myBooks))
			return false;
		return true;
	}

	/**
	 * This method allows to verify if a books is or is not in the BookStore
	 * 
	 * @param ISBN of the Book
	 * @return true if it is
	 */
	public boolean isBookOnStore(String ISBN) {
		return myBooks.containsKey(ISBN);
	}

	/**
	 * This method allows to add a new book in the library
	 * 
	 * @param ISBN          of the book
	 * @param title         of the book
	 * @param purchasePrice of the book
	 * @param salePrice     of the book
	 * @param quantity      of the book
	 * @throws RepeatedBookException if the book is already in the store
	 * @throws InvestmentException   if the book is more expensive that the
	 *                               investment of the library
	 */
	public void addBook(String ISBN, String title, double purchasePrice, double salePrice, int quantity)
			throws RepeatedBookException, InvestmentException {
		if (isBookOnStore(ISBN)) {
			throw new RepeatedBookException("El libro con ISBN: " + ISBN + " ya se encuentra en la libreria.");
		}
		if (purchasePrice > investment) {
			throw new InvestmentException(
					"El precio de compra del libro excede los recursos monetarios de la tienda: \n" + investment
							+ " inversión\n" + purchasePrice + " precio de compra del libro");
		}
		double price = quantity * purchasePrice;
		Book newBook = new Book(ISBN, title, purchasePrice, salePrice, quantity);
		myBooks.put(ISBN, newBook);
		investment -= price;

	}

	/**
	 * This method allows to remove a book
	 * 
	 * @param ISBN of the book
	 * @return the book removed
	 * @throws BookNullException if the book is not in the library
	 */
	public Book removeBook(String ISBN) throws BookNullException {
		if (!isBookOnStore(ISBN)) {
			throw new BookNullException("El libro con ISBN: " + ISBN + " no se encuentra en en la libreria.");
		}
		return myBooks.remove(ISBN);
	}

	/**
	 * This method allows to search a book by its title
	 * 
	 * @param title of the book
	 * @return the book
	 * @throws BookNullException if the books is not in the library
	 */
	public Book searchBookByTitle(String title) throws BookNullException {

		Iterator<String> iterator = myBooks.keySet().iterator();
		boolean stopper = false;
		Book auxiliar = null;
		while (iterator.hasNext() && !stopper) {
			auxiliar = myBooks.get(iterator.next());
			if (auxiliar.getTitle().toUpperCase().equals(title.toUpperCase())) {
				stopper = true;
			}
		}
		if (!stopper) {
			throw new BookNullException("El libro: " + title + " no se encuentra en la libreria.");
		}
		return auxiliar;
	}

	/**
	 * This method allows to search a book by its ISBN
	 * 
	 * @param ISBN of the book
	 * @return the book
	 * @throws BookNullException if the book is not in the library
	 */
	public Book searchBookByISBN(String ISBN) throws BookNullException {
		if (!isBookOnStore(ISBN)) {
			throw new BookNullException("El libro con ISBN: " + ISBN + " no se encuentra en la libreria.");
		}
		return myBooks.get(ISBN);
	}

	/**
	 * This method allows to supply a single book
	 * 
	 * @param ISBN     of the book
	 * @param quantity to be supplied
	 * @param date     of the transaction
	 * @throws BookNullException   if the book does not exist
	 * @throws InvestmentException if the investment is not enough to buy the books
	 * @throws QuantityException   if the quantity is minor than 0
	 */
	public void supplyBook(String ISBN, int quantity, Date date)
			throws BookNullException, InvestmentException, QuantityException {
		if (!isBookOnStore(ISBN)) {
			throw new BookNullException("El libro con ISBN: " + ISBN + " no se encuentra en la libreria.");
		}
		if (quantity <= 0) {
			throw new QuantityException("La cantidad debe ser mayor que 0.");
		}
		Book bookToBeSuplied = myBooks.get(ISBN);
		double price = bookToBeSuplied.getPurchasePrice() * quantity;
		if (price > investment) {
			throw new InvestmentException("La cantidad: " + quantity + " supera el presupuesto: " + investment);
		}
		investment -= price;
		bookToBeSuplied.supply(quantity, date);
	}

	/**
	 * This method allows to sell a book
	 * 
	 * @param ISBN     of the book
	 * @param quantity to be sell
	 * @param date     of the transaction
	 * @throws BookNullException if the book does not exist
	 * @throws QuantityException if the quantity to be sell is minor than 0
	 */
	public void toSellBook(String ISBN, int quantity, Date date) throws BookNullException, QuantityException {
		if (!isBookOnStore(ISBN)) {
			throw new BookNullException("El libro con ISBN: " + ISBN + " no se encuentra en la libreria.");
		}
		if (quantity <= 0) {
			throw new QuantityException("La cantidad a vender debe ser mayor a 0.");
		}
		Book bookToBeSell = myBooks.get(ISBN);
		double price = bookToBeSell.getSalePrice() * quantity;
		if (bookToBeSell.toSell(quantity, date)) {
			investment += price;
		}
	}

	/**
	 * this method allows to find the number of transactions that a book has
	 * 
	 * @param ISBN of the book<
	 * @return an int
	 * @throws BookNullException if the books does not exist
	 */
	public int getNumberOfTransactions(String ISBN) throws BookNullException {
		if (!isBookOnStore(ISBN)) {
			throw new BookNullException("El libro con ISBN: " + ISBN + " no se encuentra en la libreria.");
		}
		Book book = myBooks.get(ISBN);
		return book.getMyTransactions().size();
	}

	/**
	 * This method allows to find the most expensive book in the library
	 * 
	 * @return the most expensive book
	 * @throws EmptyLibraryException if the library is empty
	 */
	public Book getMostExpensiveBook() throws EmptyLibraryException {
		if (myBooks.isEmpty()) {
			throw new EmptyLibraryException("La libreria no tiene libros aún.");
		}
		Iterator<String> iterator = myBooks.keySet().iterator();
		double major = 0;
		Book auxiliar;
		Book expensiveBook = null;
		while (iterator.hasNext()) {
			auxiliar = myBooks.get(iterator.next());
			if (auxiliar.getSalePrice() > major) {
				expensiveBook = auxiliar;
				major = expensiveBook.getSalePrice();
			}
		}
		return expensiveBook;
	}

	/**
	 * This method allows to count the quantity of sales by a single book
	 * 
	 * @param ISBN of the book
	 * @return an int with the sales of the book
	 */
	private int getBookSales(String ISBN) {
		int counter = 0;
		Book auxiliar = myBooks.get(ISBN);
		Transaction auxiliarTransaction = null;
		for (int i = 0; i < auxiliar.getMyTransactions().size(); i++) {
			auxiliarTransaction = auxiliar.getMyTransactions().get(i);
			if (auxiliarTransaction.getType() == TransactionType.SALE)
				counter += auxiliarTransaction.getQuantity();
		}
		return counter;
	}

	/**
	 * This method allows to find the cheapest book
	 * 
	 * @return the cheapest book
	 * @throws EmptyLibraryException if the library is empty
	 */
	public Book getCheapestBook() throws EmptyLibraryException {
		if (myBooks.isEmpty()) {
			throw new EmptyLibraryException("La libreria no tiene libros aún.");
		}
		Iterator<String> iterator = myBooks.keySet().iterator();
		double minor = Double.MAX_VALUE;
		Book auxiliar;
		Book cheapestBook = null;
		while (iterator.hasNext()) {
			auxiliar = myBooks.get(iterator.next());
			if (auxiliar.getSalePrice() < minor) {
				cheapestBook = auxiliar;
				minor = cheapestBook.getSalePrice();
			}
		}
		return cheapestBook;
	}

	/**
	 * This method allows to find the best seller
	 * 
	 * @return the best seller if the library is not empty
	 * @throws EmptyLibraryException if the library is empty
	 * @throws BookNullException     if the books has not been sales already
	 */
	public Book getBestSeller() throws EmptyLibraryException, BookNullException {
		if (myBooks.isEmpty()) {
			throw new EmptyLibraryException("La libreria no tiene libros aún.");
		}
		Iterator<String> iterator = myBooks.keySet().iterator();
		Book auxiliar;
		Book bestSeller = null;
		int counter = 0;
		int major = 0;
		while (iterator.hasNext()) {
			auxiliar = myBooks.get(iterator.next());
			counter = getBookSales(auxiliar.getISBN());
			if (counter > major) {
				bestSeller = auxiliar;
				major = counter;
			}
		}
		if (bestSeller == null) {
			throw new BookNullException("No se han vendido libros aún");
		}
		return bestSeller;
	}

	/**
	 * This method allows to get an ArrayList with the books information
	 * 
	 * @return an ArrayList
	 */
	public ArrayList<Book> getBooksInArrayList() {
		ArrayList<Book> newArrayList = new ArrayList<Book>();
		Iterator<String> iterator = myBooks.keySet().iterator();
		while (iterator.hasNext()) {
			newArrayList.add(myBooks.get(iterator.next()));
		}
		return newArrayList;
	}

}
