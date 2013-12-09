package com.example.sampleactionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentLayout_Details extends Fragment {
	View v;
	String temid;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Bundle bd = this.getArguments();
		try {
			temid = bd.getString("positon");

			System.out.println("temid----" + temid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		v = inflater.inflate(R.layout.fragmentlay, container, false);

		TextView tv = (TextView) v.findViewById(R.id.textView1);
		tv.setText(String.valueOf(temid));
		return v;
	}
}
