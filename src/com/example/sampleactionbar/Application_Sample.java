package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//Application class is the base class where you can maintain global variables.

public class Application_Sample extends Activity {
  
    EditText scoreET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_sample);
        scoreET = (EditText) findViewById(R.id.editText1);
     }
   @Override
    protected void onResume() {
        super.onResume();
        MyApplication state = ((MyApplication) getApplicationContext());
         scoreET.setText(String.valueOf(state.getGameScore()));
    }

     public void incrementScore(View view) {
    	 MyApplication state = ((MyApplication) getApplicationContext());
         state.incrementScore();
         scoreET.setText(String.valueOf(state.getGameScore()));
     }

    public void nextScreen(View view) {
    	MyApplication state = ((MyApplication) getApplicationContext());
        state.decrementScore();
        scoreET.setText(String.valueOf(state.getGameScore()));
     }
   }