package com.example.sampleactionbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity_ForGetPost extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//MainActivity For Search like ios
//		MainSearchLayout searchLayout = new MainSearchLayout(this, null);		
//		setContentView(searchLayout);
		
		setContentView(R.layout.mainxml);
	
		Button btn1 = (Button)findViewById(R.id.button1);
		
		Button btn2 = (Button)findViewById(R.id.button2);
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity_ForGetPost.this, JsonParserSample.class));
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity_ForGetPost.this, JsonParserPostSample.class));
			}
		});
	}
}
