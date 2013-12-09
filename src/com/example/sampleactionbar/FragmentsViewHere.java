package com.example.sampleactionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentsViewHere extends Fragment {
	String tag = this.getClass().getSimpleName();
	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.d(tag, "onCreateView");
		v = inflater.inflate(R.layout.fragmentlay, container, false);
		return v;
	}
}
