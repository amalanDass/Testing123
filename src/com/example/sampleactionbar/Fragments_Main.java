package com.example.sampleactionbar;

import com.example.sampleactionbar.FragmentsOriginal.onItemClickMethod;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class Fragments_Main extends FragmentActivity implements
		onItemClickMethod {
	boolean check;
	int lastposition = -1;

	@Override
	protected void onCreate(Bundle b) {
		// TODO Auto-generated method stub
		super.onCreate(b);
		setContentView(R.layout.info_fragment);
		FrameLayout fl = (FrameLayout) findViewById(R.id.framlay);

		if (fl != null && fl.getVisibility() == View.VISIBLE)
			check = true;
		
		if(b!=null){
			int reloadlastpositon = b.getInt("last_positon");
			if(reloadlastpositon!=-1)
				ClickyPosition(reloadlastpositon);
		}
		
	}

	@Override
	public void ClickyPosition(int positon) {
		// TODO Auto-generated method stub
		this.lastposition = positon;
		Bundle bd = new Bundle();
		if (check) {
			bd.putString("positon", positon + "");
			Fragment fm = new FragmentLayout_Details();
			fm.setArguments(bd);// For passing the arguments
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.framlay, fm);
			transaction.addToBackStack(null);
			transaction.commit();
		} else {
			System.out.println("positon----" + positon);
			Intent i = new Intent(Fragments_Main.this, FragmentSecondMain.class);
			bd.putString("positon", positon + "");
			i.putExtras(bd);
			startActivity(i);

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.getInt("last_positon", lastposition);
	}
}
