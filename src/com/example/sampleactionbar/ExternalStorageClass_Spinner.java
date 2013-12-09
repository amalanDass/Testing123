package com.example.sampleactionbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalStorageClass_Spinner extends Activity implements
		OnItemSelectedListener, OnClickListener {
	TextView read, write;

	Spinner sr;

	String[] spinner_content = { "Download", "Music", "Pictures" };

	File path = null;
	File file = null;
	boolean Write, Read;
	Button save, saveas;
	EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.externalstorage);
		read = (TextView) findViewById(R.id.textView1);

		write = (TextView) findViewById(R.id.textView2);

		sr = (Spinner) findViewById(R.id.spinner1);

		save = (Button) findViewById(R.id.Save);

		saveas = (Button) findViewById(R.id.Confirm_Save);

		et = (EditText) findViewById(R.id.et_save);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ExternalStorageClass_Spinner.this,
				android.R.layout.simple_selectable_list_item, spinner_content);

		sr.setAdapter(adapter);

		sr.setOnItemSelectedListener(this);
		save.setOnClickListener(this);
		saveas.setOnClickListener(this);

		// Tutorial no 105 completed start 106

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position = sr.getSelectedItemPosition();
		switch (position) {
		case 0:
			path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			System.out.println("FILE PATH--1" + path);
			break;
		case 1:
			path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
			System.out.println("FILE PATH--2" + path);
			break;
		case 2:
			path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			System.out.println("FILE PATH--3" + path);
			break;

		}

	}

	private void checkstate() {
		// TODO Auto-generated method stub
		String check_sd = Environment.getExternalStorageState();

		if (check_sd.equals(Environment.MEDIA_MOUNTED)) {
			// Yes you can read and write.
			read.setText("true");
			write.setText("true");
			Write = Read = true;
		} else if ((check_sd.equals(Environment.MEDIA_MOUNTED_READ_ONLY))) {
			// You can read but not write.
			read.setText("true");
			write.setText("false");
			Write = false;
			Read = true;
		} else {
			read.setText("false");
			write.setText("false");
			Write = false;
			Read = false;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Save:

			String file_name = et.getText().toString();

			file = new File(path, file_name + ".png");

			checkstate();
			if (Write = Read = true) {
				path.mkdir();
				try {
					InputStream is = getResources().openRawResource(
							R.drawable.marker);
					OutputStream os = new FileOutputStream(file);
					byte[] data = new byte[is.available()];
					is.read(data);
					os.write(data);
					is.close();
					os.close();

					Toast.makeText(getApplicationContext(),
							"Success fully Saved", Toast.LENGTH_LONG).show();

					// Update File for the user to use
					MediaScannerConnection
							.scanFile(
									getApplicationContext(),
									new String[] { file.toString() },
									null,
									new MediaScannerConnection.OnScanCompletedListener() {

										@Override
										public void onScanCompleted(
												String path, Uri uri) {
											// TODO Auto-generated method stub
											Toast.makeText(
													getApplicationContext(),
													"Scane complete",
													Toast.LENGTH_LONG).show();
										}
									});
					// Update File for the user to use
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case R.id.Confirm_Save:
			save.setVisibility(View.VISIBLE);
			break;
		}
	}

}
