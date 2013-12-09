package com.example.sampleactionbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Http_Get {
	BufferedReader bf = null;
	String result = null;
public String GETMethos(){	
	try {
		HttpClient hc = new DefaultHttpClient();
	    URI uri = new URI("http://www.mybringback.com");
		HttpGet get = new HttpGet();
	    get.setURI(uri);
	    HttpResponse res = hc.execute(get);
	  
	    bf = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));   
	    
	    StringBuffer sb = new StringBuffer("");
	    String l ="";
	    while((l=bf.readLine())!=null ){
	    	sb.append(l+"\n");
	    }
	    bf.close();
	    result= sb.toString();
	    return result;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	    catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
}