package co.crisi.bookstore.controller;

import java.net.URL;

import java.util.ResourceBundle;

import co.crisi.bookstore.application.Main;
import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.EmptyLibraryException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.model.BookStore;
import co.crisi.bookstore.model.Transaction;
import co.crisi.bookstore.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MenuViewController {

	private MainViewController mainController;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label investmentLabel;

	@FXML
	private ListView<Book> listView;

	@FXML
	private Label ISBNLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label purchasePriceLabel;

	@FXML
	private Label salePriceLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private TextField ISBNField;

	@FXML
	private TextField titleField;

	@FXML
	private TextField purchasePriceField;

	@FXML
	private TextField salePriceField;

	@FXML
	private TableView<Transaction> tableView;

	@FXML
	private TableColumn<Transaction, String> dateTableColumn;

	@FXML
	private TableColumn<Transaction, String> typeTableColumn;

	@FXML
	private TableColumn<Transaction, String> quantityTableColumn;

	@FXML
	void handleAddBookButton(ActionEvent event) {
		mainController.showAddBooks(listView);

	}

	@FXML
	void handleGetBestSellerButton(ActionEvent event) {
		BookStore bookStore = Main.getBookStore();
		Book searched;
		try {
			searched = bookStore.getBestSeller();
			refreshDataField(searched);
			selectItemtTableView(searched);
		} catch (EmptyLibraryException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		} catch (BookNullException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		}

	}

	@FXML
	void handleGetCheapestBookButton(ActionEvent event) {
		BookStore bookStore = Main.getBookStore();
		Book searched;
		try {
			searched = bookStore.getCheapestBook();
			refreshDataField(searched);
			selectItemtTableView(searched);
		} catch (EmptyLibraryException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		}

	}

	@FXML
	void handleGetMostExpensiveBookButton(ActionEvent event) {
		BookStore bookStore = Main.getBookStore();
		Book searched;
		try {
			searched = bookStore.getMostExpensiveBook();
			refreshDataField(searched);
			selectItemtTableView(searched);
		} catch (EmptyLibraryException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		}

	}

	@FXML
	void handleRemoveButton(ActionEvent event) {
		if (!listView.getSelectionModel().isEmpty()) {
			Book book = listView.getSelectionModel().getSelectedItem();
			BookStore bookStore = Main.getBookStore();
			try {
				bookStore.removeBook(book.getISBN());
				listView.getItems().remove(book);
				listView.refresh();
				mainController.showAlert("Libro: " + book.toString() + " eliminado", "INFORMACIÓN", "",
						AlertType.INFORMATION);
			} catch (BookNullException e) {
				e.printStackTrace();
			}
		} else {
			mainController.showAlert("Debes seleccionar algún libro", "ADVERTENCIA", "", AlertType.WARNING);
		}
	}

	@FXML
	void handleSearchByISBNButton(ActionEvent event) {
		BookStore bookStore = Main.getBookStore();
		String ISBNToSearch = MainViewController.readMessage("Ingrese el ISBN del libro", "INGRESAR ISBN");
		try {
			Book searched = bookStore.searchBookByISBN(ISBNToSearch);
			refreshDataField(searched);
			selectItemtTableView(searched);
		} catch (BookNullException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		}

	}

	@FXML
	void handleSearchByTitleButton(ActionEvent event) {
		BookStore bookStore = Main.getBookStore();
		String titleToSearch = MainViewController.readMessage("Ingrese el título del libro", "INGRESAR TÍTULO");
		try {
			Book searched = bookStore.searchBookByTitle(titleToSearch);
			refreshDataField(searched);
			selectItemtTableView(searched);
		} catch (BookNullException e) {
			mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
		}

	}

	@FXML
	void handleSellButton(ActionEvent event) {
		if (!listView.getSelectionModel().isEmpty()) {
			Book book = listView.getSelectionModel().getSelectedItem();
			String quantityStr = MainViewController.readMessage("Ingrese la cantidad a vender", "INGRESAR CANTIDAD");
			BookStore bookStore = Main.getBookStore();
			try {
				bookStore.toSellBook(book.getISBN(), Integer.parseInt(quantityStr), new Date());
				mainController.showAlert("Transacción correcta", "INFORMACIÓN", "", AlertType.INFORMATION);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BookNullException e) {
				e.printStackTrace();
			} catch (QuantityException e) {
				mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			}
		} else {
			mainController.showAlert("Debes seleccionar un libro", "ADVERTENCIA", "", AlertType.WARNING);
		}
	}

	@FXML
	void handleSupplyButton(ActionEvent event) {
		if (!listView.getSelectionModel().isEmpty()) {
			Book book = listView.getSelectionModel().getSelectedItem();
			String quantityStr = MainViewController.readMessage("Ingrese la cantidad a abastecer", "INGRESAR CANTIDAD");
			BookStore bookStore = Main.getBookStore();
			try {
				bookStore.supplyBook(book.getISBN(), Integer.parseInt(quantityStr), new Date());
				mainController.showAlert("Transacción correcta", "INFORMACIÓN", "", AlertType.INFORMATION);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BookNullException e) {
				e.printStackTrace();
			} catch (InvestmentException e) {
				mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			} catch (QuantityException e) {
				mainController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			}

		} else {
			mainController.showAlert("Debe seleccionar un libro", "ADVERTENCIA", "", AlertType.WARNING);
		}

	}

	@FXML
	void handleSeeDataButton(ActionEvent event) {
		if (!listView.getSelectionModel().isEmpty()) {
			Book book = listView.getSelectionModel().getSelectedItem();
			refreshDataLabel(book);
		} else {
			mainController.showAlert("Debe seleccionar algún libro", "ADVERTENCIA", "", AlertType.WARNING);
		}

	}

	private void refreshDataLabel(Book book) {
		ISBNLabel.setText(book.getISBN());
		purchasePriceLabel.setText("$" + book.getPurchasePrice());
		quantityLabel.setText(book.getQuantity() + "");
		salePriceLabel.setText("$" + book.getSalePrice());
		titleLabel.setText(book.getTitle());
	}

	private void refreshDataField(Book book) {
		ISBNField.setText(book.getISBN());
		purchasePriceField.setText("$" + book.getPurchasePrice());
		salePriceField.setText("$" + book.getSalePrice());
		titleField.setText(book.getTitle());
	}

	@FXML
	void initialize() {
		assert investmentLabel != null : "fx:id=\"investmentLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert ISBNLabel != null : "fx:id=\"ISBNLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert purchasePriceLabel != null : "fx:id=\"purchasePriceLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert salePriceLabel != null : "fx:id=\"salePriceLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert quantityLabel != null : "fx:id=\"quantityLabel\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert ISBNField != null : "fx:id=\"ISBNField\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert titleField != null : "fx:id=\"titleField\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert purchasePriceField != null : "fx:id=\"purchasePriceField\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert salePriceField != null : "fx:id=\"salePriceField\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert dateTableColumn != null : "fx:id=\"dateTableColumn\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert typeTableColumn != null : "fx:id=\"typeTableColumn\" was not injected: check your FXML file 'MenuView.fxml'.";
		assert quantityTableColumn != null : "fx:id=\"quantityTableColumn\" was not injected: check your FXML file 'MenuView.fxml'.";
	}

	public MainViewController getMainController() {
		return mainController;
	}

	public void setMainController(MainViewController mainController) {
		this.mainController = mainController;
		initViews();
	}

	public void initListView() {
		listView.setItems(Main.getBookData());
		listView.refresh();
	}

	public void initTableView() {
		dateTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		quantityTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
		typeTableColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
	}

	public void initViews() {
		initListView();
		initTableView();
	}

	public void selectItemtTableView(Book book) {
		ObservableList<Transaction> transactionsData = FXCollections.observableList(book.getMyTransactions());
		tableView.setItems(transactionsData);
		tableView.refresh();
	}

	public void setListView(ListView<Book> listView) {
		this.listView = listView;
	}


}
