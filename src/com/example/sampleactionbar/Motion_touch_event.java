package com.example.sampleactionbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Motion_touch_event extends Activity {
	 long start,end;
	  @Override	 
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    (findViewById(R.id.button1)).setVisibility(View.VISIBLE);
	    
	    (findViewById(R.id.button1)).setOnTouchListener(new OnTouchListener()
        {
			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getAction() == MotionEvent.ACTION_DOWN){
					start = e.getEventTime();
				}
				if(e.getAction() == MotionEvent.ACTION_UP){
					end = e.getEventTime();
				}
				if(end - start>1500){
					AlertDialog ad = new AlertDialog.Builder(Motion_touch_event.this).create();
					ad.setTitle("Pick An Option");
					ad.setMessage("Motion touch event");
					
					ad.setButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ad.setButton2("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ad.setButton3("Not Now", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ad.show();
					return true;
				}
				return false;
				
				
			}
       });
	  }
	} 
