package com.kgm.kNotes;

public class Note {

	private String title = "";
	private String note  = "";
	private String date  = "";
	
	public Note() {
		
	}
	
	public Note(String title, String note, String date) {
		this.title = title;
		this.note  = note;
		this.date  = date;
	}
	
	public String getTitle() {
		return title;
	} 
	
	public String getNote() {
		return note;
	}
	
	public String getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
