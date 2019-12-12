package controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.ViewType;

public class MainController implements Initializable 
{
	private static MainController instance = null;
	@FXML private BorderPane borderPane;
	@FXML private Label timeStamp;
	@FXML private Button newEvent;
	@FXML private Button stopAll;
	@FXML private Button runSchedule;
	@FXML private Button editEvent;
	@FXML private Button deleteEvent;
	@FXML private Button pauseSchedule;

	@FXML private ListView listView;

	public static ViewType currentView;

	private MainController() {

	}

	public static MainController getInstance() {
		if(instance == null)
			instance = new MainController();
		return instance;
	}

	public void switchView(ViewType view) 
	{
		MyController controller = null;
		String viewString = "";

		switch(view) 
		{
		case VIEW1:	viewString = "../view/AddEvent.fxml";
		controller = new AddEventController();
		currentView = ViewType.VIEW1;
		break;
		case VIEW2:
			break;
		default:
		}

		try
		{
			URL url = this.getClass().getResource(viewString);
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(controller);
			Parent viewNode = loader.load();
			borderPane.setCenter(viewNode);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		displayListView();
	}
	
	void displayListView() {
		File directoryPath = new File(".");
		File[] files=directoryPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".m3u");
			}
		});

		ArrayList<String> m3uFiles = new ArrayList<String>();
		for (File file : files) {
			String name = file.getName();
			m3uFiles.add(name.substring(0,name.length()-4));
		}

		ObservableList<String> obs = FXCollections.observableArrayList();
		obs.addAll(m3uFiles);
		listView.setItems(obs);
		listView.setStyle("-fx-font-size: 20px;");
	}

	@FXML
	void clickedNewEvent(ActionEvent event) {
		if (!(currentView == ViewType.VIEW1)) 
			switchView(ViewType.VIEW1);
	}

	@FXML
	void clickedDeleteEvent(ActionEvent event) {
		//TODO : delete from scheduled tasks also
		if(listView.getSelectionModel().getSelectedItem() != null)
		{
			String fileName = listView.getSelectionModel().getSelectedItem().toString() + ".m3u";
			new File(fileName).delete();
			displayListView();
		}
	}

	@FXML
	void homePage() {

	}

	@FXML
	void clickedEditEvent(ActionEvent event) {

	}

	@FXML
	void clickedPauseSchedule(ActionEvent event) {

	}

	@FXML
	void clickedRunSchedule(ActionEvent event) {

	}

	@FXML
	void clickedStopAll(ActionEvent event) {

	}


	//-------------ACCESSORS-------------//
	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

}
