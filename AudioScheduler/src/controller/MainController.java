package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
    @FXML private TableView<?> table;
    @FXML private TableColumn<?, ?> eventTime;
    @FXML private TableColumn<?, ?> eventName;
    @FXML private TableColumn<?, ?> eventFile;
    
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

	}

	@FXML
	void clickedNewEvent(ActionEvent event) {
		if (!(currentView == ViewType.VIEW1)) 
			switchView(ViewType.VIEW1);
	}

	@FXML
	void clickedDeleteEvent(ActionEvent event) {

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
