package com.kgm.kNotes;

import com.kgm.kNotes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Main extends Activity {
	public Main() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lay_main);
		
		Button btn = (Button) findViewById(R.id.btn_new);
		
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					setContentView(R.layout.lay_new);

					Button btn = (Button) findViewById(R.id.btn_add);
					
					if (btn != null) {
						btn.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								
							}
						});
					}
				}
			});
		}
	}
}
