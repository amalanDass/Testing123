package com.example.sampleactionbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StoreIntenalClass extends Activity implements OnClickListener {
	public static final String finalstring = "static_data";
	EditText et;
	TextView vt;
	FileOutputStream fos;
	FileInputStream fis;
	String collected = null;
	ProgressDialog pd;
	public static final String FILENAME="StoreIntenalClass";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			try {
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(save.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
		case R.id.load:
			new loadAcyncTask().execute(FILENAME);
			/*String collected = null;
			try {
				fis = openFileInput(FILENAME);
				byte[] bytesvalue = new byte[fis.available()];
				while(fis.read(bytesvalue) != -1 ){
					collected = new String(bytesvalue);
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				vt.setText(collected);
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			break;
		}
	}
	public class loadAcyncTask extends AsyncTask<String, Integer, String>{//Params, Progress, Result
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(StoreIntenalClass.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMax(100);
			pd.show();
		}
		@SuppressWarnings("finally")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			for(int i =0;i<20;i++){
				publishProgress(5);
				try {
					Thread.sleep(88);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				fis = openFileInput(FILENAME);
				byte[] bytesvalue = new byte[fis.available()];
				while(fis.read(bytesvalue) != -1 ){
					collected = new String(bytesvalue);
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return collected;
			}
		}
		@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
				pd.incrementProgressBy(values[0]);
			}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			vt.setText(result);
		}
		
	}
	
}
