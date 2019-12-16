package controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
	@FXML private MenuItem about;
	@FXML private MenuItem homePage;

	@FXML private ListView listView;
	private ArrayList<String> filesData;

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
		case VIEW1:	viewString = "/view/AddEvent.fxml";
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
			Main.stage.setTitle("Add Event");
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
		
		filesData = m3uFiles;
		

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
		String fileName = "";
		if(listView.getSelectionModel().getSelectedItem() != null)
		{
			String taskName = listView.getSelectionModel().getSelectedItem().toString();
			fileName = taskName + ".m3u";
			new File(fileName).delete();

			String command = "SCHTASKS /DELETE /TN \"MyTasks\\"+ taskName + "\" /f";
			String command1 = "SCHTASKS /DELETE /TN \"MyTasks\\"+ taskName + "stop\" /f";

			command = command.replace("\\\\", "\\");
			System.out.println(command);
			Runtime rt = Runtime.getRuntime();
			try
			{
				rt.exec(new String[] {
						"cmd.exe",
						"/c",
						command,
				});
				rt.exec(new String[] {
						"cmd.exe",
						"/c",
						command1,
				});
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			displayListView();
		}
	}

	@FXML
	void homePage() {
		try 
		{
			URL url = Main.class.getResource("/view/MainView.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			MainController controller = MainController.getInstance();
			loader.setController(controller);
			Parent rootNode = loader.load();			
			controller.setBorderPane((BorderPane) rootNode);
			Main.stage.setScene(new Scene(rootNode));
			Main.stage.getIcons().add(new Image("file:logo/logo.png"));
			Main.stage.setTitle("Audio Scheduler");
			Main.stage.show();
			MainController.currentView = ViewType.HOME;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void clickedEditEvent(ActionEvent event) {

	}

	@FXML
	void clickedPauseSchedule(ActionEvent event) {

	}

	@FXML
	void clickedRunSchedule(ActionEvent event) {
		for(String s : filesData)
			tasker(s,"ENABLE");
	}

	@FXML
	void clickedStopAll(ActionEvent event) {
		for(String s : filesData)
			tasker(s,"DISABLE");
	}
	
	@FXML
	void credits(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Made by your very Awesome friend Vishal :) ");
		alert.showAndWait();
	}
	
	void tasker(String taskName, String change) {
		String filePath = new File(".").getAbsolutePath();
		filePath = filePath.substring(0,filePath.length()-1);

		String command = "SCHTASKS /CHANGE /TN \"MyTasks\\"+ taskName + "\" /"+ change;
		String command1 = "SCHTASKS /CHANGE /TN \"MyTasks\\"+ taskName + "stop\" /"+ change;

		command = command.replace("\\\\", "\\");
		System.out.println(command);
		Runtime rt = Runtime.getRuntime();
		try
		{
			rt.exec(new String[] {
					"cmd.exe",
					"/c",
					command
			});
			rt.exec(new String[] {
					"cmd.exe",
					"/c",
					command1
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	//-------------ACCESSORS-------------//
	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

}
