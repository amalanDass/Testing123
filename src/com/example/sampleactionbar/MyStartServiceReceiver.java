package com.example.sampleactionbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartServiceReceiver  extends BroadcastReceiver {

	  @Override
	  public void onReceive(Context context, Intent intent) {
		  System.out.println("MyStartServiceReceiver");
	    Intent i = new Intent(context, LocalWordService.class);
	    context.startService(i);
	  }
	} 
