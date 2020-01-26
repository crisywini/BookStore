package co.crisi.bookstore.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.crisi.bookstore.application.Main;
import co.crisi.bookstore.model.Book;
import co.crisi.bookstore.view.Finder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane mainPane;

	@FXML
	private ImageView mainImage;
	private Main main;
	private Stage primaryStage;
	VBox menuView;
	VBox addBooksView;
	MenuViewController menuViewController;
	AddBooksViewController addBooksController;
	Stage addBooksStage;

	@FXML
	void initialize() {
		assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MainView.fxml'.";
		assert mainImage != null : "fx:id=\"mainImage\" was not injected: check your FXML file 'MainView.fxml'.";
		mainImage.setImage(new Image("file:resources/images/bookStore.png"));
		showMenuView();
	}

	public void showMenuView() {
		if (menuView == null) {
			try {
				FXMLLoader loader = new FXMLLoader(Finder.class.getResource("MenuView.fxml"));
				menuView = (VBox) loader.load();
				menuViewController = loader.getController();
				menuViewController.setMainController(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mainPane.setCenter(menuView);
	}

	public void showAddBooks(ListView<Book> books) {
		try {
			FXMLLoader loader = new FXMLLoader(Finder.class.getResource("AddBooksView.fxml"));
			addBooksView = (VBox) loader.load();
			Scene scene = new Scene(addBooksView);
			addBooksController = loader.getController();
			addBooksController.setMainController(this);
			addBooksController.setBooks(books);
			addBooksStage = new Stage();
			addBooksStage.setTitle("Agregar libros");
			addBooksStage.initOwner(primaryStage);
			addBooksStage.initModality(Modality.WINDOW_MODAL);
			addBooksStage.setScene(scene);
			addBooksStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void showAlert(String contentText, String title, String headerText, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setContentText(contentText);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	public static String readMessage(String message, String title) {
		String out = "";
		// Muestra el mensaje
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle(title);
		dialog.setHeaderText("");
		dialog.setContentText(message);

		// Forma tradicional de obtener la resuesta.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			out = result.get();
		}

		return out;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
