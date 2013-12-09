package com.example.sampleactionbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.usefullclass.BasePostParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class JsonParserPostSample extends Activity{
	String url;ProgressDialog pd;String response = "";
	//JSONObject obj,obj1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new NewUser_Tash().execute();	
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
    		pd=new ProgressDialog(JsonParserPostSample.this);
    		pd.setMessage("Loading......");
    		pd.show();
            }
		protected Boolean doInBackground(String... params) {
			
			
			 	try {
/*					String requestParam = "{\"Actions\":{\"Action\":["
							+ "{\"UserId\":\"" + userid + "\"," + "\"DeviceType\":\""
							+ Global.deviceType + "\"," + "\"Format\":\"json\","
							+ "\"AppVersion\":\"" + Global.appVersion + "\","
							+ "\"DeviceId\":\"" + deviceId + "\"," + "\"Members\":["
							+ widgetString + "]," + "\"AppName\":\"" + Global.appName
							+ "\"" + "}]}}";*/

					JSONObject jsonObject;

					try {
						jsonObject = new JSONObject();//requestParam
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
						
						nameValuePairs.add(new BasicNameValuePair("username", "admin"));
						nameValuePairs.add(new BasicNameValuePair("password", "admin123"));
						nameValuePairs.add(new BasicNameValuePair("format", "json"));
//						nameValuePairs.add(new BasicNameValuePair("api_key", Global.apiKey));
//						nameValuePairs.add(new BasicNameValuePair("device_id", deviceId));
//						nameValuePairs.add(new BasicNameValuePair("city_name",Global.cityStringNormal));
						nameValuePairs.add(new BasicNameValuePair("user_custom",URLEncoder.encode(jsonObject.toString(), "UTF-8")));
						
						//http://218.248.19.62/skpit/?q=my_services/user/login.json
						BasePostParser b = new BasePostParser("http://218.248.19.62/skpit/?q=my_services/user/login" , nameValuePairs);
						JSONObject responseObj = b.getJsonObject();
						System.out.println(">> server response: " + responseObj);
						
						response = responseObj.getJSONObject("user").getString("name");
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
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
					
					//obj1 = obj.getJSONObject("response");
					
					System.out.println("XXXXXXXXXXXXXx"+response);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				Toast.makeText(JsonParserPostSample.this,"Invalid email id and password",Toast.LENGTH_SHORT).show();
				finish();
			}
		
		
		}
    }
}
