package com.example.sampleactionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class Handler_Sample extends Activity  { 
	  private TextView txtStatus;
	  private RefreshHandler mRedrawHandler = new RefreshHandler();
	 
	    @SuppressLint("HandlerLeak")
		class RefreshHandler extends Handler {
	        
	    @Override
	    public void handleMessage(Message msg) {
	    	Handler_Sample.this.updateUI();
	    }
	 
	    public void sleep(long delayMillis) {
	      this.removeMessages(0);
	      sendMessageDelayed(obtainMessage(0), 1500);
	    }
	  };
	 
	  private void updateUI(){
	    int currentInt = Integer.parseInt((String) txtStatus.getText()) + 10;
	    if(currentInt <= 100){
	      mRedrawHandler.sleep(1000);
	      txtStatus.setText(String.valueOf(currentInt));
	 
	    }
	  }
	 
	  @Override 
	  public void onCreate(Bundle icicle) { 
	    super.onCreate(icicle); 
	    setContentView(R.layout.handler);
	    txtStatus = (TextView) this.findViewById(R.id.textView1);
	   updateUI();
	  } 
	}