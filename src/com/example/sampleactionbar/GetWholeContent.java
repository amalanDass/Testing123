package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetWholeContent extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getwholecontent);
		
		TextView tv = (TextView)findViewById(R.id.textView1);
		
		Http_Get hg = new Http_Get();
		
		tv.setText(hg.GETMethos().toString());

	}
}
