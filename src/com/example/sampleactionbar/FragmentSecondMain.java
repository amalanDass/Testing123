package com.example.sampleactionbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentSecondMain extends FragmentActivity {
	String positon;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.fragment_second);
		super.onCreate(arg0);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
		}

		Intent bd = this.getIntent();
		Bundle mBundle = new Bundle();
		mBundle.putString("positon", bd.getStringExtra("positon"));

		Fragment fm = new FragmentLayout_Details();
		fm.setArguments(mBundle);// For passing the arguments
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.framlay, fm);
		transaction.commit();

	}
}
