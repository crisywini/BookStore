package co.crisi.bookstore.controller;

import java.net.URL;
import java.util.ResourceBundle;

import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.RepeatedBookException;
import co.crisi.bookstore.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class AddBooksViewController {

	private MainViewController mainController;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField ISBNField;

	@FXML
	private TextField titleField;

	@FXML
	private TextField purchasePriceField;

	@FXML
	private TextField salePriceField;

	@FXML
	private TextField quantityField;
	private ListView<Book> books;

	@FXML
	void handleAddButton(ActionEvent event) {
		if (isInputValid()) {
			try {
				mainController.getMain().addBook(ISBNField.getText(), titleField.getText(),
						Double.parseDouble(purchasePriceField.getText()), Double.parseDouble(salePriceField.getText()),
						Integer.parseInt(quantityField.getText()));
				Book book = mainController.getMain().searchBookByISBN(ISBNField.getText());
				books.getItems().add(book);
				books.refresh();
				ISBNField.setText("");
				purchasePriceField.setText("");
				quantityField.setText("");
				salePriceField.setText("");
				titleField.setText("");
				mainController.showAlert("Libro: " + book.toString() + " agregado", "INFORMACIÓN", "",
						AlertType.INFORMATION);
				mainController.addBooksStage.hide();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RepeatedBookException e) {
				mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			} catch (InvestmentException e) {
				mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			} catch (BookNullException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void handleExitButton(ActionEvent event) {
		mainController.addBooksStage.hide();
	}

	@FXML
	void initialize() {
		assert ISBNField != null : "fx:id=\"ISBNField\" was not injected: check your FXML file 'AddBooksView.fxml'.";
		assert titleField != null : "fx:id=\"titleField\" was not injected: check your FXML file 'AddBooksView.fxml'.";
		assert purchasePriceField != null : "fx:id=\"purchasePriceField\" was not injected: check your FXML file 'AddBooksView.fxml'.";
		assert salePriceField != null : "fx:id=\"salePriceField\" was not injected: check your FXML file 'AddBooksView.fxml'.";
		assert quantityField != null : "fx:id=\"quantityField\" was not injected: check your FXML file 'AddBooksView.fxml'.";

	}

	public MainViewController getMainController() {
		return mainController;
	}

	public void setMainController(MainViewController mainController) {
		this.mainController = mainController;
	}

	public boolean isInputValid() {
		boolean isValid = false;
		String errorMessage = "";
		if (ISBNField.getText().isEmpty())
			errorMessage += "Debe ingresar el ISBN del libro\n";
		if (titleField.getText().isEmpty())
			errorMessage += "Debe ingresar el título del libro\n";
		if (purchasePriceField.getText().isEmpty()) {
			errorMessage += "Debe igresar el precio de compra del libro\n";
		} else {
			try {
				Double.parseDouble(purchasePriceField.getText());
			} catch (Exception e) {
				errorMessage += "El precio de compra debe ser un número\n";
			}
		}
		if (salePriceField.getText().isEmpty()) {
			errorMessage += "Debe ingresar el precio de venta del libro\n";
		} else {
			try {
				Double.parseDouble(salePriceField.getText());
			} catch (Exception e) {
				errorMessage += "El precio de venta debe ser un número\n";
			}
		}
		if (quantityField.getText().isEmpty()) {
			errorMessage += "Debe ingresar el número de unidades\n";
		} else {
			try {
				Integer.parseInt(quantityField.getText());
			} catch (Exception e) {
				errorMessage += "Las unidades deben ser un número\n";
			}
		}
		if (errorMessage.length() == 0) {
			isValid = true;
		} else {
			mainController.showAlert(errorMessage, "ADVERTENCIA", "", AlertType.WARNING);
		}
		return isValid;
	}

	public void setBooks(ListView<Book> books) {
		this.books = books;
	}
}
