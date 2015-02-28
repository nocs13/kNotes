package com.kgm.kNotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.kgm.kNotes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Main extends Activity {
	private Notes notes = null;
	private String dir = "";
	private int layid = -1;
	
	public Main() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainLayer();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (layid == R.layout.lay_main) {
	    		finish();
	    	} else if (layid == R.layout.lay_new) {
	    		mainLayer();
	    	} else if (layid == R.layout.lay_note) {
	    		mainLayer();
	    	} else if (layid == R.layout.lay_wrong) {
	    		//newLayer();
	    		mainLayer();
	    	}
	        // your code
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	public void mainLayer()
	{
		setContentView(R.layout.lay_main);
		layid = R.layout.lay_main;
		
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
	    		layid = R.layout.lay_note;
	    		
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
	    					notes.remove(note);
	    					
	    					mainLayer();
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
					newLayer();
				}
			});
		}
	}
	
	public void newLayer()
	{
		setContentView(R.layout.lay_new);
		layid = R.layout.lay_new;

		Button btn = (Button) findViewById(R.id.btn_add);
		
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					EditText title = (EditText) findViewById(R.id.et_new_title);
					EditText note = (EditText) findViewById(R.id.et_new_note);
					
					if (title.getText().toString().length() < 1 || note.getText().toString().length() < 1) {
						
						setContentView(R.layout.lay_wrong);
						layid = R.layout.lay_wrong;
						
						return;
					}
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Date today = Calendar.getInstance().getTime();        
					String date = df.format(today);

					Note n = new Note(title.getText().toString(), note.getText().toString(), date);
					
					notes.add(n);

					mainLayer();
				}
			});
		}
	}
}
