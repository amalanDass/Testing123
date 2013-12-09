package com.example.sampleactionbar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

@SuppressWarnings("deprecation")
public class SlidingDrawar extends Activity implements OnClickListener, OnDrawerOpenListener, OnCheckedChangeListener{
	Button btn1,btn2,btn3,btn4;
	 SlidingDrawer sd;
	 MediaPlayer mp ;
	 CheckBox cb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slide);
	
	    cb = (CheckBox)findViewById(R.id.checkBox1);
	    btn1 = (Button)findViewById(R.id.button1);
	    btn2 = (Button)findViewById(R.id.button2);
	    btn3 = (Button)findViewById(R.id.button3);
	    btn4 = (Button)findViewById(R.id.button4);
	
	    sd= (SlidingDrawer)findViewById(R.id.slidingDrawer1);
	    //sd.setOnClickListener(this);
	    sd.setOnDrawerOpenListener(this);
	    cb.setOnCheckedChangeListener(this);
	    
	    btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    btn3.setOnClickListener(this);
	    btn4.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button1:
			sd.open();
			break;
		case R.id.button2:
			sd.close();
			break;
		case R.id.button3:
			sd.toggle();
			break;
		case R.id.button4:
			break;
		}
	}

	@Override
	public void onDrawerOpened() {
		// TODO Auto-generated method stub
		mp= MediaPlayer.create(getApplication(), R.raw.bb);
		mp.start();
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg0.isChecked())
			sd.lock();
		else
			sd.unlock();
	}
	
}
