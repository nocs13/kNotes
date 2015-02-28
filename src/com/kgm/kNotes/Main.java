package com.kgm.kNotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.kgm.kNotes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Main extends Activity {
	private Notes notes = null;
	private String dir = "";
	
	public Main() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lay_main);
		
		dir = this.getApplicationInfo().dataDir;
		
		notes = new Notes(dir);

		ListView lv = (ListView) findViewById(R.id.lst_notes);	
		
		try {

			final ArrayList<Note> al = notes.getNotes();

			ArrayAdapter aa = new ArrayAdapter(this, R.layout.sample_note_view, al);

			lv.setAdapter(aa);
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {	        
	        final Note note = al.get(position);
	        
	    		setContentView(R.layout.lay_note);
	    		
	    		TextView tv = (TextView) findViewById(R.id.lnote_tv_title);
	    		tv.setText(note.getTitle());
	    		tv = (TextView) findViewById(R.id.lnote_tv_date);
	    		tv.setText(note.getDate());
	    		tv = (TextView) findViewById(R.id.lnote_tv_note);
	    		tv.setText(note.getNote());
	    		
	    		Button btn = (Button) findViewById(R.id.lnote_btn_remove);
	    		
	    		if (btn != null) {
	    			btn.setOnClickListener(new OnClickListener() {
	    				public void onClick(View v) {
	    					setContentView(R.layout.lay_main);
	    					
	    					notes.remove(note);
	    				}
	    			});
	    		}
	      }

	    });

		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.println("Main::Main error: " + e.getMessage());
		}
		
		Button btn = (Button) findViewById(R.id.btn_new);		
		
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					setContentView(R.layout.lay_new);

					Button btn = (Button) findViewById(R.id.btn_add);
					
					if (btn != null) {
						btn.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								EditText title = (EditText) findViewById(R.id.et_new_title);
								EditText note = (EditText) findViewById(R.id.et_new_note);
								
								if (title.getText().toString().length() < 1 || note.getText().toString().length() < 1) {
									
									setContentView(R.layout.lay_wrong);
									
									return;
								}
								
								DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
								Date today = Calendar.getInstance().getTime();        
								String date = df.format(today);

								Note n = new Note(title.getText().toString(), note.getText().toString(), date);
								
								notes.add(n);
								
								setContentView(R.layout.lay_main);
							}
						});
					}
				}
			});
		}
	}
	
	public void refresh() {
		dir = this.getApplicationInfo().dataDir;
		
		notes = new Notes(dir);

		ListView lv = (ListView) findViewById(R.id.lst_notes);
		
		lv.removeAllViews();
		
		try {
			ArrayList<Note> al = notes.getNotes();

			ArrayAdapter aa = new ArrayAdapter(this, R.layout.sample_note_view, al);

			lv.setAdapter(aa);			

		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.println("Main::Main error: " + e.getMessage());
		}
	}
}
