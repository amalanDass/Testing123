package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

public class ViewFlipper_Sample extends Activity implements OnClickListener{
	ViewFlipper vf;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.viewflipper);
	
	vf =(ViewFlipper)findViewById(R.id.viewFlipper1);
	
	vf.setOnClickListener(this);
	vf.setInAnimation(ViewFlipper_Sample.this,android.R.anim.fade_in);
	vf.setOutAnimation(ViewFlipper_Sample.this, android.R.anim.fade_out);
	vf.setFlipInterval(500);
	vf.startFlipping();
 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		vf.showNext();
	}
}
