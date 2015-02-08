package com.kgm.kNotes;

import com.kgm.kNotes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	public Main() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button btn = null;
		
		View v = new View(this.getBaseContext());

		super.onCreate(savedInstanceState);

		setContentView(v);
	}
}
