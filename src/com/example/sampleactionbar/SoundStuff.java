package com.example.sampleactionbar;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class SoundStuff extends Activity{
	SoundPool sp;
	int explotion=0;
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View v = new View(this);
		setContentView(v);
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		explotion = sp.load(getApplication(), R.raw.bb, 1);
		mp = MediaPlayer.create(getApplication(), R.raw.test);

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(explotion!=0)
				sp.play(explotion,1, 1, 0, 0, 1);
			}
		});
		v.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				mp.start();
				return false;
			}
		});
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
	}
	
}
