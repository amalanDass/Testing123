package com.example.sampleactionbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class GetWhole_WebContent extends Activity {
	 
	  private TextView text;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.thread);
	    text = (TextView) findViewById(R.id.textView1);
	    SampleTask ex = new SampleTask();
	    ex.execute(new String[] { "http://www.vogella.com" });
	  }
	  
	  public class SampleTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
		      String response = "";
		      for (String url : params) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          InputStream content = execute.getEntity().getContent();

		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          String s = "";
		          while ((s = buffer.readLine()) != null) {
		            response += s;
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return response;
		      }
		 @Override
		    protected void onPostExecute(String result) {
			 text.setText(result);
		    }

	  }
	} 
