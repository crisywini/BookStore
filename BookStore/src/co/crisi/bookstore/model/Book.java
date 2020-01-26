package co.crisi.bookstore.model;

import java.io.Serializable;
import java.util.LinkedList;

import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Crisi Sánchez Pineda
 *
 */
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ISBN;
	private String title;
	private double purchasePrice;
	private double salePrice;
	private int quantity;
	private LinkedList<Transaction> myTransactions;

	public Book() {
		this("$#$#$", "NONE", 99999, 99999, 0);
	}

	public Book(String ISBN, String title, double purchasePrice, double salePrice, int quantity) {
		this.ISBN = ISBN;
		this.title = title;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.quantity = quantity;
		myTransactions = new LinkedList<Transaction>();
	}

	public String getISBN() {
		return ISBN;
	}

	public StringProperty ISBNProperty() {
		return new SimpleStringProperty(ISBN);
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public StringProperty titleProperty() {
		return new SimpleStringProperty(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LinkedList<Transaction> getMyTransactions() {
		return myTransactions;
	}

	public void setMyTransactions(LinkedList<Transaction> myTransactions) {
		this.myTransactions = myTransactions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		result = prime * result + ((myTransactions == null) ? 0 : myTransactions.hashCode());
		long temp;
		temp = Double.doubleToLongBits(purchasePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		temp = Double.doubleToLongBits(salePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		if (myTransactions == null) {
			if (other.myTransactions != null)
				return false;
		} else if (!myTransactions.equals(other.myTransactions))
			return false;
		if (Double.doubleToLongBits(purchasePrice) != Double.doubleToLongBits(other.purchasePrice))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(salePrice) != Double.doubleToLongBits(other.salePrice))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * This method allows to supply the quantity of this book
	 * 
	 * @param quantity to supply, should be major than 0
	 * @param date     of the transaction
	 * @throws QuantityException if the quantity of books is less than 0
	 */
	public void supply(int quantity, Date date) throws QuantityException {

		if (quantity > 0) {
			Transaction transaction = new Transaction(date, quantity, TransactionType.SUPPLYING, this);

			myTransactions.add(transaction);

			this.quantity += quantity;
		} else
			throw new QuantityException("La cantidad a añadir debe ser mayor a 0");

	}

	/**
	 * This method allows to sell one or more books
	 * 
	 * @param quantity to be sell
	 * @param date     of the transaction
	 * @return true if the quantity is major or equal to 0
	 * @throws QuantityException if the quantity is major or less than 0
	 */
	public boolean toSell(int quantity, Date date) throws QuantityException {
		boolean sell = false;
		if (quantity <= 0) {
			throw new QuantityException("La cantidad a vender debe ser mayor a 0.");
		}
		if (this.quantity < quantity) {
			throw new QuantityException("La cantidad: " + quantity + " no está disponible.");
		} else {
			int out = this.quantity - quantity;

			Transaction newTransaction = new Transaction(date, quantity, TransactionType.SALE, this);
			myTransactions.add(newTransaction);

			setQuantity(out);
			sell = true;
		}
		return sell;
	}

	@Override
	public String toString() {
		return "<" + ISBN + "> " + title;
	}

}
