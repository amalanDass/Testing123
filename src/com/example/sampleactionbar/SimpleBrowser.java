package com.example.sampleactionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class SimpleBrowser extends Activity implements OnClickListener {
	EditText et;
	Button go,back,forward,refresh,clear;
	WebView wv;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);
		
		
		wv = (WebView)findViewById(R.id.webview);
		wv.loadUrl("http://www.mycityway.com/about");
		wv.getSettings().setJavaScriptEnabled(true);//You tube or anyother websit can loab becasue we enable the JavaScript.
		wv.getSettings().setUseWideViewPort(true);// Fully zooom out the web site.
		wv.getSettings().setLoadWithOverviewMode(true);
		
		wv.setWebViewClient(new webvieClient());
		
		et =(EditText)findViewById(R.id.url);
		
		go = (Button)findViewById(R.id.go);
		back = (Button)findViewById(R.id.back);
		forward = (Button)findViewById(R.id.forword);
		refresh = (Button)findViewById(R.id.refresh);
		clear= (Button)findViewById(R.id.clear);
		
		go.setOnClickListener(this);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
		clear.setOnClickListener(this);
	}
	
	public class webvieClient extends WebViewClient {

		public boolean shouldOverRideUrlLoader(WebView v, String url){
			v.loadUrl(url);
			return true;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.go:
			String uurl = et.getText().toString();
			wv.loadUrl(uurl);
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
			break;
		case R.id.back:
			if(wv.canGoBack())
				wv.goBack();
			break;
		case R.id.forword:
			if(wv.canGoForward())
				wv.goForward();
			break;
		case R.id.refresh:
				wv.reload();
			break;
		case R.id.clear:
			wv.clearHistory();
		break;
		}
	}

}
