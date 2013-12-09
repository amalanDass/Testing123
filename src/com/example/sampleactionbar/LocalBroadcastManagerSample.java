package com.example.sampleactionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalBroadcastManagerSample extends Activity{
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.broadcastreceiver);
	    //FAQ
	    /*
	     Register the receiver in onResume() method
	     Send Broad cast Receiver
	     Unregistered Receiver to the OnPasue() method
	     */
		  EditText text = (EditText) findViewById(R.id.time);
		    text.setVisibility(View.GONE);
	  }
	public void startAlert(View view) {
	    
	    Log.d("sender", "Broadcasting message");
	    Intent intent = new Intent("custom-event-name");
	    // You can also include some extra data.
	    intent.putExtra("message", "This is my message!");
	    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	    
	  }
	
	// Our handler for received Intents. This will be called whenever an Intent
		// with an action named "custom-event-name" is broadcasted.
		private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		  @SuppressLint("ShowToast")
		@Override
		  public void onReceive(Context context, Intent intent) {
		    // Get extra data included in the Intent
		    String message = intent.getStringExtra("message");
		    Log.d("receiver", "Got message: " + message);
		    Toast.makeText(context,  "Got message: "+ message, Toast.LENGTH_SHORT).show();
		  }
		};
		@Override
		protected void onPause() {// Unregister since the activity is about to be closed.
		  LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		  super.onDestroy();
		}
		@Override
		protected void onResume() { // // Register to receive messages.
			// TODO Auto-generated method stub
			super.onResume();
			  // We are registering an observer (mMessageReceiver) to receive Intents
			  // with actions named "custom-event-name".
			  LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
			      new IntentFilter("custom-event-name"));
		}
}
