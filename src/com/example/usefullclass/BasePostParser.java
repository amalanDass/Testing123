package com.example.usefullclass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.util.Log;

public class BasePostParser {

	private String TAG = "BasePostParser";
	private String url = "";
	private List<NameValuePair> nameValuePairs;

	public BasePostParser(String url, List<NameValuePair> nameValuePairs) {
		this.url = url;
		this.nameValuePairs = nameValuePairs;
	}

	public JSONObject getJsonObject() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			System.out.println("posting data URL: " + url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse resp = httpclient.execute(httppost);
			HttpEntity entity = resp.getEntity();
			String respStr = "";
			if (entity != null) {
				respStr = convertResponseToString(resp, entity);
			}
			return new JSONObject(respStr);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "" + e);
		}
		return null;

	}

	private String convertResponseToString(HttpResponse response,
			HttpEntity entity) {

		String resp = "";
		try {
			InputStream inputStream = entity.getContent();
			if (response.containsHeader("Content-Encoding")
					&& response.getLastHeader("Content-Encoding").toString()
							.contains("gzip")) {
				Log.e(TAG, "GZipped data");
				GZIPInputStream gzip = new GZIPInputStream(inputStream);
				StringBuffer szBuffer = new StringBuffer();
				byte tByte[] = new byte[1024];
				while (true) {
					int iLength = gzip.read(tByte, 0, 1024);
					if (iLength < 0)
						break;
					szBuffer.append(new String(tByte, 0, iLength));
				}
				resp = szBuffer.toString();
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						inputStream));
				Log.e(TAG, "Normal data");
				StringBuffer responseString = new StringBuffer();
				String temp = null;
				while ((temp = in.readLine()) != null) {
					responseString.append(temp);
				}
				resp = responseString.toString();

				System.out.println("resp->" + resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error while convertResponseToString " + e);
		}

		return resp;
	}

}
