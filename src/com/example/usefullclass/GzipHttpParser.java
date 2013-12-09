package com.example.usefullclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import com.example.usefullclass.Utils;

public class GzipHttpParser {

	private String url = "";

	public GzipHttpParser(String url) {
		this.url = url;
	}

	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SOCKET_TIMEOUT = 10000;

	public JSONObject getJsonObject() {
		JSONObject jsonParser = null;

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

		httpClient.addRequestInterceptor(new GzipHttpRequestInterceptor());
		httpClient.addResponseInterceptor(new GzipHttpResponseInterceptor());

		try {

			HttpResponse response = httpClient.execute(new HttpGet(url));
			
			HttpEntity entity = response.getEntity();
			String resp;
			if (entity != null) {
				resp = convertResponseToString(response, response.getEntity());
				resp=resp.replace("&apos;", "'");
//				Utils.logx("serveresp:" + resp);
				jsonParser = new JSONObject(resp);
			}else{
				jsonParser = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utils.log("error loading:" + url);
		}
		return jsonParser;
	}
	
	

	private String convertResponseToString(HttpResponse response, HttpEntity entity) {

		String resp="";
		try{
			InputStream inputStream = entity.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer responseString = new StringBuffer();
			String temp = null;
			while((temp = in.readLine()) != null){
				responseString.append(temp);
			}
			resp=responseString.toString();
		}catch (Exception e) {
			Utils.log("Error while convertResponseToString "+e);
		}
		return resp;
	}
	
	

	private class GzipHttpRequestInterceptor implements HttpRequestInterceptor {
		public void process(final HttpRequest request, final HttpContext context) {
			if (!request.containsHeader("Accept-Encoding")) {
				request.addHeader("Accept-Encoding", "gzip");
			}
		}
	}

	private class GzipHttpResponseInterceptor implements
			HttpResponseInterceptor {
		public void process(final HttpResponse response,
				final HttpContext context) {
			final HttpEntity entity = response.getEntity();
			final Header encoding = entity.getContentEncoding();
			if (encoding != null) {
				inflateGzip(response, encoding);
			}
		}

		private void inflateGzip(final HttpResponse response,
				final Header encoding) {
			for (HeaderElement element : encoding.getElements()) {
				if (element.getName().equalsIgnoreCase("gzip")) {
					response.setEntity(new GzipInflatingEntity(response
							.getEntity()));
					break;
				}
			}
		}
	}

	private class GzipInflatingEntity extends HttpEntityWrapper {
		public GzipInflatingEntity(final HttpEntity wrapped) {
			super(wrapped);
		}

		@Override
		public InputStream getContent() throws IOException {
			return new GZIPInputStream(wrappedEntity.getContent());
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}
}
