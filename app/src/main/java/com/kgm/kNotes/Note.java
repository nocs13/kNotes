package com.kgm.kNotes;

public class Note {
	private String note  = "";
	private String date  = "";
	
	public Note() {
		
	}
	
	public Note(String note, String date) {
		this.note  = note;
		this.date  = date;
	}
	
	public String getNote() {
		return note;
	}
	
	public String getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		String text;

		if(note.length() > 20){
			text = note.substring(0, 19);
			text += "...";
		}
		else{
			text = note;
		}

		return text;
	}
}
