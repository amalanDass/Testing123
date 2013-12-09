package AndroidInterviewQuestionsandAnswer;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class InterviewQuestionAns {
/*
 *) What abt different Kinds of context?
 Ans: Context is a current state of the application/object.
 You can get the context by invoking 
 getApplicationContext(), getContext(), getBaseContext() or this (when in the activity class).
 
 *) Different kinds of Intents types?
 Ans:implesit intent: Intent.Action_View (Browser Intent,ShareIntent)
 explicit intent:  
 statActivity(intent)
 StartActivityForResult(intent);
 PendingIntent(intent);
 
 *) Different Storage Methods in android
 Ans:
Shared Preferences
Store private primitive data in key-value pairs.
Internal Storage
Store private data on the device memory.
External Storage
Store public data on the shared external storage.
SQLite Databases
Store structured data in a private database.
Network Connection
Store data on the web with your own network server.
 
 *) How do you get your device id?
 TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
 tm.getDeviceId();
 
  *) How do you find the network connection?
  ConnectivityManager mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); 
  NetworkInfo info = mConnectivity.getActiveNetworkInfo();
 
  *) How do you find gps lat and log?
  LocationManager locationMgr = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
 
 
 
 
 
 */	
}
