package co.crisi.bookstore.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import co.crisi.bookstore.controller.MainViewController;
import co.crisi.bookstore.exceptions.BookNullException;
import co.crisi.bookstore.exceptions.EmptyLibraryException;
import co.crisi.bookstore.exceptions.InvestmentException;
import co.crisi.bookstore.exceptions.QuantityException;
import co.crisi.bookstore.exceptions.RepeatedBookException;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.model.BookStore;
import co.crisi.bookstore.persistence.Persistence;
import co.crisi.bookstore.util.Date;
import co.crisi.bookstore.view.Finder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application implements IBookStoreControl {
	private static BookStore bookStore;
	private static ObservableList<Book> bookData = FXCollections.observableArrayList();
	final EventHandler<WindowEvent> closer = new EventHandler<WindowEvent>() {

		@Override
		public void handle(WindowEvent event) {
			saveData();
		}
	};

	@Override
	public void start(Stage primaryStage) {
		loadData();
		showMain(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void showMain(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(Finder.class.getResource("MainView.fxml"));
			BorderPane pane = (BorderPane) loader.load();
			Scene scene = new Scene(pane);
			MainViewController controller = loader.getController();
			controller.setMain(this);
			controller.setPrimaryStage(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(closer);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BookStore getBookStore() {
		return bookStore;
	}

	public static void setBookStore(BookStore bookStore) {
		Main.bookStore = bookStore;
	}

	// PERSISTENCE
	public void loadData() {
		File file = new File("persistenceData/BookStore.dat");
		if (file.exists()) {
			try {
				BookStore auxiliar = (BookStore) Persistence.readObject();
				setBookStore(auxiliar);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			bookStore = new BookStore();
		}
		bookData.setAll(bookStore.getBooksInArrayList());
	}

	public static void saveData() {
		try {
			Persistence.serializeObject(bookStore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// END OF PERSISTENCE

	// SERVICES

	public static ObservableList<Book> getBookData() {
		return bookData;
	}

	public static void setBookData(ObservableList<Book> bookData) {
		Main.bookData = bookData;
	}

	@Override
	public void addBook(String ISBN, String title, double purchasePrice, double salePrice, int quantity)
			throws RepeatedBookException, InvestmentException {
		bookStore.addBook(ISBN, title, purchasePrice, salePrice, quantity);

	}

	@Override
	public Book removeBook(String ISBN) throws BookNullException {
		return bookStore.removeBook(ISBN);
	}

	@Override
	public Book searchBookByTitle(String title) throws BookNullException {
		return bookStore.searchBookByTitle(title);
	}

	@Override
	public Book searchBookByISBN(String ISBN) throws BookNullException {
		return bookStore.searchBookByISBN(ISBN);
	}

	@Override
	public void supplyBook(String ISBN, int quantity, Date date)
			throws BookNullException, InvestmentException, QuantityException {
		bookStore.supplyBook(ISBN, quantity, date);

	}

	@Override
	public void toSellBook(String ISBN, int quantity, Date date) throws BookNullException, QuantityException {
		bookStore.toSellBook(ISBN, quantity, date);
	}

	@Override
	public int getNumberOfTransactions(String ISBN) throws BookNullException {
		return bookStore.getNumberOfTransactions(ISBN);
	}

	@Override
	public Book getMostExpensiveBook() throws EmptyLibraryException {
		return bookStore.getMostExpensiveBook();
	}

	@Override
	public Book getCheapestBook() throws EmptyLibraryException {
		return bookStore.getCheapestBook();
	}

	@Override
	public Book getBestSeller() throws EmptyLibraryException, BookNullException {
		return bookStore.getBestSeller();
	}

	@Override
	public ArrayList<Book> getBooksInArrayList() {
		return bookStore.getBooksInArrayList();
	}
	// END OF SERVICES
}
