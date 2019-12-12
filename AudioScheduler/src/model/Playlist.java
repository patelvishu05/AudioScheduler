package model;

import java.util.Date;
import java.util.HashMap;

public class Playlist {
	
	private Date startTime;
	private Date endTime;
	private String filePath;
	private HashMap<String,Boolean> routine;
	
	public Playlist() {
		routine.put("sunday", false);
		routine.put("monday", false);
		routine.put("tuesday", false);
		routine.put("wednesday", false);
		routine.put("thursday", false);
		routine.put("friday", false);
		routine.put("saturday", false);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public HashMap<String, Boolean> getRoutine() {
		return routine;
	}

	public void setRoutine(HashMap<String, Boolean> routine) {
		this.routine = routine;
	}
	

	
	
}
