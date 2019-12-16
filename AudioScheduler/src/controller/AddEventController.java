package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

	void displayHomePage() {
		try 
		{
			URL url = Main.class.getResource("/view/MainView.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			MainController controller = MainController.getInstance();
			loader.setController(controller);
			Parent rootNode = loader.load();			
			controller.setBorderPane((BorderPane) rootNode);
			Main.stage.setScene(new Scene(rootNode));
			Main.stage.show();
			MainController.currentView = ViewType.HOME;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@FXML
	void clickedSave() {
		if(mp3Files == null)
			return;
//		if(shuffle.isSelected())
//			Collections.shuffle(mp3Files);
		
		FileWriter fw = null;
		
		try
		{
			fw = new FileWriter(name.getText() + ".m3u");
			for(File f : mp3Files)
				fw.append(f.toString()+ "\n");
			fw.close();
			
			createTask(name.getText(),startTime.getText(),endTime.getText());
			displayHomePage();
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
	
	void createTask(String taskName, String startTime, String stopTime) throws IOException 
	{
		String filePath = new File(".").getAbsolutePath();
		filePath = filePath.substring(0,filePath.length()-1);
		System.out.println(filePath);
		
		String command = 	"SCHTASKS /CREATE /SC DAILY" + 
							" /TN \"MyTasks\\" + taskName + "\"" + 
							" /TR \"PowerShell.exe -WindowStyle hidden -Command Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass; " +
							"Start-Process vlc.exe " + filePath + "\\" + taskName + ".m3u\"" +
							" /ST " + startTime;
		
		String command2 = "SCHTASKS /CREATE /SC DAILY"+
						  " /TN \"MyTasks\\" + taskName +"stop\""+ 
						  " /TR \"taskkill /IM vlc.exe /F\" "
//						  " /TR \"" + filePath + "\\stopVlc.bat\" "
						  + "/ST " + stopTime;
				
		command = command.replace("\\\\", "\\");
		command2 = command2.replace("\\\\", "\\");
		System.out.println(command);
		Runtime rt = Runtime.getRuntime();
		rt.exec(new String[] {
			"cmd.exe",
			"/c",
			command
		});
		
		System.out.println(command2);
		rt.exec(new String[] {
				"cmd.exe",
				"/c",
				command2
			});
		
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
//FileWriter fw = new FileWriter(taskName + ".ps1");
//fw.append(("Start-Process vlc.exe " + filePath + "\\" + taskName + ".m3u").replace("\\\\", "\\"));
//System.out.println(fw.toString());
//fw.close();
