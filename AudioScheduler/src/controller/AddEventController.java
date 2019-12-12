package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Playlist;
import model.ViewType;

public class AddEventController implements MyController {

	@FXML private CheckBox sunday;
	@FXML private CheckBox monday;
	@FXML private CheckBox tuesday;
	@FXML private CheckBox wednesday;
	@FXML private CheckBox thursday;
	@FXML private CheckBox friday;
	@FXML private CheckBox saturday;
	@FXML private TextArea startTime;
	@FXML private TextArea endTime;
	@FXML private CheckBox shuffle;
	@FXML private Button chooseFile;
	@FXML private TextArea name;
	@FXML private Button save;

	Playlist playlist;
	List<File> mp3Files = null;

	@FXML
	void getSelectedDay() {
		playlist.getRoutine().put("sunday", sunday.isSelected());
		playlist.getRoutine().put("monday", monday.isSelected());
		playlist.getRoutine().put("tuesday", tuesday.isSelected());
		playlist.getRoutine().put("wednesday", wednesday.isSelected());
		playlist.getRoutine().put("thursday", thursday.isSelected());
		playlist.getRoutine().put("friday", friday.isSelected());
		playlist.getRoutine().put("saturday", saturday.isSelected());
	}

	@FXML
	void shufflePlaylist() {

	}

	@FXML
	void clickedSave() {
		if(mp3Files == null)
			return;
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(name.getText() + ".m3u");
			for(File f : mp3Files)
				fw.append(f.toString()+ "\n");
			fw.close();
			
			URL url = Main.class.getResource("../view/MainView.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			MainController controller = MainController.getInstance();
			loader.setController(controller);
			Parent rootNode = loader.load();			
			controller.setBorderPane((BorderPane) rootNode);
			Main.stage.setScene(new Scene(rootNode));
			Main.stage.show();
			MainController.currentView = ViewType.HOME;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	public void clickedChooseFile() {
		mp3Files = chooseFiles();

	}

	public List<File> chooseFiles() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("MP3 Files", "*.mp3"));
		List<File> selected = chooser.showOpenMultipleDialog(Main.stage);

		return selected != null? selected : new ArrayList<File>();
	}

}

//
//@FXML
//public void clickedSave() {
//	
////	Media m = new Media("file:///C:/Users/Vishu/Desktop/Scripts/Shri_Swaminarayan_Arti_Ashtak_Evening_001.mp3");
//	MediaPlayer p;// = new MediaPlayer(m);
////	p.play();
//	
//	ArrayList<String> files = new ArrayList<String>();
//
//	files.add("file:///C:/Users/Vishu/Desktop/dhuliBHajans/DeckTheHalls.mp3");
//	files.add("file:///C:/Users/Vishu/Desktop/Scripts/Shri_Swaminarayan_Arti_Ashtak_Evening_001.mp3");
//	Media m = new Media(files.get(0));
//	p = new MediaPlayer(m);
//	
//	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	Date date = null;
//	try {
//		date = df.parse("2019-12-11 20:17:00");
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	new Timer().schedule(new TimerTask() {
//		@Override
//		public void run() {
//			p.play();				
//		}
//	}, date);
//	
//	
//	
//	
////	playlist = new Playlist();
////	getSelectedDay();		
////	
////	try 
////	{
////		URL url = Main.class.getResource("../view/MainView.fxml");
////		FXMLLoader loader = new FXMLLoader(url);
////		MainController controller = MainController.getInstance();
////		loader.setController(controller);
////		Parent rootNode = loader.load();			
////		controller.setBorderPane((BorderPane) rootNode);
////		Main.stage.setScene(new Scene(rootNode));
////		Main.stage.show();
////		MainController.currentView = ViewType.HOME;
////	} catch (IOException e) {
////		e.printStackTrace();
////	}	
//}
