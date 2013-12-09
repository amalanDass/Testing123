package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EditTExt extends Activity implements OnFocusChangeListener{
	EditText txtEdit1,txtEdit2,txtEdit3;
	RelativeLayout bottom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.edittext);
	    
	    txtEdit1 = (EditText) findViewById(R.id.editText1);

	    txtEdit1.setOnFocusChangeListener(this);
	    
	    txtEdit2 = (EditText) findViewById(R.id.editText2);

	    txtEdit2.setOnFocusChangeListener(this);
	    
	    txtEdit3 = (EditText) findViewById(R.id.editText3);

	    txtEdit3.setOnFocusChangeListener(this);
	    
	    bottom = (RelativeLayout) findViewById(R.id.bottom);
	    
	 /*   txtEdit.setOnFocusChangeListener(new OnFocusChangeListener() {          

	           public void onFocusChange(View v, boolean hasFocus) {
	               if(!hasFocus)
	                  //do job here owhen Edittext lose focus 
	           }
	       });*/
	    
	    
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.editText1:
			if(hasFocus){
			System.out.println("EDIT TEXT ONE");
			bottom.setVisibility(View.VISIBLE);}
		break;
		case R.id.editText2:
			if(hasFocus){
			System.out.println("EDIT TEXT TWO");
			bottom.setVisibility(View.VISIBLE);}
		break;
		case R.id.editText3:
			if(hasFocus){
			System.out.println("EDIT TEXT THREE");
			bottom.setVisibility(View.GONE);}
		break;
		}
	}	
}
