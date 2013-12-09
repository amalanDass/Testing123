package com.example.sampleactionbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferencesClass extends Activity implements OnClickListener {
public static final String finalstring = "static_data";
SharedPreferences prefs;
EditText et;
TextView vt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);

		prefs = getSharedPreferences(finalstring, 0);
		
		Button save = (Button) findViewById(R.id.save);
		Button load = (Button) findViewById(R.id.load);
		vt = (TextView) findViewById(R.id.tv_load);
		et = (EditText) findViewById(R.id.ed_load);

		save.setOnClickListener(this);
		load.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save:
            String save = et.getText().toString();
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("MyStoredString", save);
			editor.commit();
            break;
		case R.id.load:
			String load = prefs.getString("MyStoredString", "couldn't load data");
			vt.setText(load);
			break;
		}
	}
}
