package application;

import java.net.URL;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		stage = primaryStage;
		URL url = getClass().getResource("../view/MainView.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		MainController controller = MainController.getInstance();
		loader.setController(controller);
		Parent rootNode = loader.load();
		controller.setBorderPane((BorderPane) rootNode);
		stage.setScene(new Scene(rootNode));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
