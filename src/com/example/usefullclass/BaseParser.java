package com.example.usefullclass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.Html;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class BaseParser {

	String TAG="BaseParser";
	String url="";

	public BaseParser(String url, boolean htmlEncode){
		if(htmlEncode){
			this.url=Html.fromHtml(url).toString();//.replaceAll(" ", "%20").replaceAll("'", "%27").replaceAll("\\", "");//%5C
		}else{
			this.url=url;	
		}
	}
	
	public BaseParser(String url){
		this.url=url; //.replaceAll(" ", "%20").replaceAll("'", "%27").replaceAll("\\", "");//%5C
	}

	public JSONObject getJsonObject(){
		Log.e(TAG, "url->"+url);
		String resp="";
		try{
			resp=doHttpGet(url);
			//Log.v(TAG, ""+resp);
			resp=resp.replace("&apos;", "'");
			JSONObject jsonObject = new JSONObject(resp);
			//cacheData()
			return jsonObject;
		}catch (JSONException e) {
			Log.e(TAG, "XCCCCCCCCCCCCCC");
			 e.printStackTrace();
			return null;
		}catch (Exception e) {
			Log.e(TAG, ""+ e);
			return null;
		}
	}


	public String doHttpGet(String url) throws ClientProtocolException, IOException {
		String resp=null;

		
		
		
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 30000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 50000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient httpclient = new DefaultHttpClient(httpParameters);

		HttpGet httpget = new HttpGet(url); 

		httpget.setHeader("Content-Type","application/json");
		httpget.setHeader("Accept","application/json");
		
	

		HttpResponse response;
		response = httpclient.execute(httpget);

		//Log.i("Response Status ",response.getStatusLine().getStatusCode()+"");

		HttpEntity entity = response.getEntity();

		if (entity != null) {

			resp=convertResponseToString(response, entity);	
		}

		return resp;
	}

	private String convertResponseToString(HttpResponse response, HttpEntity entity) {

		String resp="";
		try{
			InputStream inputStream = entity.getContent();
			if(response.containsHeader("Content-Encoding")&&response.getLastHeader("Content-Encoding").toString().contains("gzip")){
				//Log.e(TAG, "GZipped data");
				GZIPInputStream  gzip = new GZIPInputStream (inputStream);
				StringBuffer  szBuffer = new StringBuffer ();
				byte  tByte [] = new byte [1024];
				while (true)
				{
					int  iLength = gzip.read (tByte, 0, 1024); 
					if (iLength < 0)
						break;
					szBuffer.append (new String (tByte, 0, iLength));
				}
				resp=szBuffer.toString();
			}else{
				BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
				//Log.e(TAG, "Normal data");
				StringBuffer responseString = new StringBuffer();
				String temp = null;
				while((temp = in.readLine()) != null){
					responseString.append(temp);
				}
				resp=responseString.toString();

				//System.out.println("resp->"+resp);
			}	
		}catch (Exception e) {
			//Log.e(TAG, "Error while convertResponseToString "+e);
		}

		return resp;
	}


}
