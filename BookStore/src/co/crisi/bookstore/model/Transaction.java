package co.crisi.bookstore.model;

import java.io.Serializable;

import co.crisi.bookstore.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Crisi Sánchez Pineda
 *
 */
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private int quantity;
	private TransactionType type;
	private Book associatedBook;
	private String id;

	public Transaction() {
		this(new Date(), 0, TransactionType.SUPPLYING, new Book());
	}

	public Transaction(Date date, int quantity, TransactionType type, Book associatedBook) {
		this.date = date;
		this.quantity = quantity;
		this.type = type;
		this.associatedBook = associatedBook;
		id = "<" + hashCode() + "/" + type + "/" + quantity + ">";
	}

	public Date getDate() {
		return date;
	}

	public StringProperty dateProperty() {
		return new SimpleStringProperty(date.toString());
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public StringProperty quantityProperty() {
		return new SimpleStringProperty(quantity + "");
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TransactionType getType() {
		return type;
	}

	public StringProperty typeProperty() {
		return new SimpleStringProperty(type.toString());
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Book getAssociatedBook() {
		return associatedBook;
	}

	public void setAssociatedBook(Book associatedBook) {
		this.associatedBook = associatedBook;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Transaction other = (Transaction) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (quantity != other.quantity)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
