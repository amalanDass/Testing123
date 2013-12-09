package com.example.sampleactionbar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BroadCastReceiverSample extends Activity{
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.broadcastreceiver);	 
	}

	  public void startAlert(View view) {
	    EditText text = (EditText) findViewById(R.id.time);
	    int i = Integer.parseInt(text.getText().toString());
	    Intent intent = new Intent(this, MyBroadcastReceiver.class);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
	    
	    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	        + (i * 1000), pendingIntent);
	    
	    
	    Toast.makeText(this, "Alarm set in " + i + " seconds",
	        Toast.LENGTH_LONG).show();
	  }
}
