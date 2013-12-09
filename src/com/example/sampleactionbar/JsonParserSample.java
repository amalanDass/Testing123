package com.example.sampleactionbar;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.usefullclass.BaseParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class JsonParserSample extends Activity{
	
	String url;ProgressDialog pd;
	JSONObject obj,obj1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		url = "http://delhiway.in/Android/Orlando/service_call_park_info.php?format=&category_name=Disney_Hotel_Transport&park_id=P4&page=1";
		
		new NewUser_Tash().execute(url);	
		
	}
	public class NewUser_Tash extends AsyncTask<String,Void,Boolean>
    {
	 String value1;
	   JSONObject root_obj,status_obj,item_obj;
		 
		JSONArray item_arr;
		
    		@Override
    		protected void onPreExecute() 
    		{
    		if(pd!=null)
    		{
    			pd.dismiss();
    		}pd=null;
    		pd=new ProgressDialog(JsonParserSample.this);
    		pd.setMessage("Loading......");
    		pd.show();
            }
		protected Boolean doInBackground(String... params) {
			 try {
				// System.out.println("YYYoneYYYY"+params[0]);
				 
				 BaseParser b = new BaseParser(params[0]);
				
				 obj = b.getJsonObject();
				
				 return true;
			 }
			 catch (Exception e) {
					e.printStackTrace();
					return false;
				} 
				}
		@Override
		protected void onPostExecute(Boolean result) {
		
			try {
					if(pd!=null && pd.isShowing()){
					pd.dismiss();
				}
			} catch (Exception e) {}
			
			if(result){
				
				try {
					
					obj1 = obj.getJSONObject("response");
					
					System.out.println("XXXXXXXXXXXXXx"+obj1);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				Toast.makeText(JsonParserSample.this,"Invalid email id and password",Toast.LENGTH_SHORT).show();
				finish();
			}
		
		
		}
    }
}
