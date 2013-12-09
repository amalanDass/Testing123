package com.example.sampleactionbar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SampleListClass extends ListActivity{
	String obj1[] ={
			"Android_LifeCycle",
			"DashboardActivity",
			"SoundStuff",
			"MapVersionOne_ExtendsMapActivity",
			"CameraPhoto",
			"Preference",
			"EditTExt",
			"MultiplsChoice_Listview",
			"AnimationClass",
			"SurfaceViewExample",
			"MainActivity_ForGetPost",
			"SlidingDrawar",
			"Tab",
			"SimpleBrowser",
			"ViewFlipper_Sample",
			"SharedPreferencesClass",
			"StoreIntenalClass",
			"VideoViewr",
			"MediaPlaybackActivity",
			"MainActivity",
			"ExternalStorageClass_Spinner",
			"DBSqlite_Url",
			"SQLSample",
			"MapVersionTwo_FragmentMapActivity",
			"Motion_touch_event",
			"Location_Manager",
			"GetWholeContent",
			"Widget_Stuff",
			"ContentProviderSample",
			"BroadCastReceiverSample",      // Broadcase Receiver to do in two way 1. Local Braod Case Receive 
			"LocalBroadcastManagerSample",	//2. Ordinary Broadcast receiver
			"Thread_ProgressTestActivity",
			"GetWhole_WebContent",
			"Handler_Sample",
			"Application_Sample",
			"Services_Sample_IntentServiceEx",  //onHandleIntent(Intent intent) having the IntentService exends class having
			"Services_consume_local",
			"Fragments_SampleStrats",            //Static Simple Fragments Only Layouts
			"Fragments_Main"  // Reference:       https://www.youtube.com/watch?v=ABRhft8fVyU&list=EL-dXvv1sF3mg
	  		
	
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(SampleListClass.this, android.R.layout.simple_list_item_1, obj1));
	
	//Timer Task for ever sertine period this will be calling
		//Timer myTimer = new Timer();
		//MyTimerTask myTimerTask= new MyTimerTask();
		//to Stop
		//myTimer.cancel();
		//to start
		//myTimer.scheduleAtFixedRate(myTimerTask, 0, 600000);
		
	//Similarly using Thread to call every 1 min the method will call 	
		/*		new Thread(new Runnable()
				{
		
				@SuppressLint("NewApi")
				public void run(){
		
				 while(true){
		
				  try{
		
				    h.post(new Runnable(){
		
				         public void run(){
		
				              go();
		
				           }
		
		
				      });
				    TimeUnit.MINUTES.sleep(1);
		
				 }catch(Exception ex){
				 }
				 }
				}
				}).start();*/
		
		
	}
	
	private class MyTimerTask extends TimerTask {
	    @Override
	    public void run() {
	        runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	               //code to get and send location information

	            }
	        });
	    }
	}
	public void go(){

	// This method is called every 10 minutes

		System.out.println("Timer Task is calling for every 1 min");
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String classname = obj1[position];
		try {
		Class cls = Class.forName("com.example.sampleactionbar."+classname);
		Intent in = new Intent(SampleListClass.this,cls);
		startActivity(in);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	// FAQ  AsyncTask parameters--> AsyncTask<Params, Progress, Result>
	// public class sampleAsync extends AsyncTask<Params, Progress, Result>
	// onReceive method having two parameters onReceive(Context context, Intent intent)                 
	// onActivityResult method having three parameters (int requestCode, int resultCode,Intent data) 
	  
	//What are Fragments awailable in Andorid
	//*ListFragment, DialogFragment, PreferenceFragment,WebViewFragment.
	//public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	
	//What is Fragments?
	//Reusuable code of UI 
	
	
	
	
	
	
	
	
}