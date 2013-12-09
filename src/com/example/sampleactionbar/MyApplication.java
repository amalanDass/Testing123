package com.example.sampleactionbar;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

public class MyApplication extends Application {
	/*
	  onConfigurationChanged( ) Called by the system when the device configuration changes while your component is running.
	  onCreate( ) Called when the application is starting, before any other application objects have been created.
	  onLowMemory( ) This is called when the overall system is running low on memory, and would like actively running processes to tighten their belts.
	  onTerminate( ) This method is for use in emulated process environments. 
	  It will never be called on a production Android device, where processes are removed by simply killing them; no user code (including this callback) is executed when doing so.
	  */
    private int gameScore = 0;

    public int getGameScore() {
         return gameScore;
    }

     public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
     }

    public void incrementScore(){
        gameScore++;
     }
    public void decrementScore(){
        gameScore--;
     }
    
    /*   Imbuilt Methods   */
   @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Toast.makeText(getApplicationContext(), "ON CONFIGURATION CHANGED", Toast.LENGTH_LONG).show();
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getApplicationContext(), "ON CREATE", Toast.LENGTH_LONG).show();
	}
 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Toast.makeText(getApplicationContext(), "ON LOW MEMORY", Toast.LENGTH_LONG).show();
	}
 
	@Override
	public void onTerminate() {
		super.onTerminate();
		Toast.makeText(getApplicationContext(), "ON TERMINATE MEMORY", Toast.LENGTH_LONG).show();
	}
 
    
}