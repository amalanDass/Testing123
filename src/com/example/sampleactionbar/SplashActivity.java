package com.example.sampleactionbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity{
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		mp= MediaPlayer.create(SplashActivity.this, R.raw.test);
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		boolean check = sp.getBoolean("alarm", true);
		if(check)
		mp.start();
		
		Thread td = new Thread(){
			@Override
			public void run(){
				try {
					sleep(1000);
					
					Intent i = new Intent(SplashActivity.this,SampleListClass.class);
					startActivity(i);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		td.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
		finish();
	}
	
}
