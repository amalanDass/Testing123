package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentsOriginal extends Fragment implements OnItemClickListener {
	String tag = this.getClass().getSimpleName();
	View v;
	ArrayAdapter<String> adapter;
	ListView lv;
	String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "f" };
	onItemClickMethod handler;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		handler = (onItemClickMethod) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(tag, "onCreateView");

		// handler = (onItemClickMethod) getActivity();

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, arr);

		v = inflater.inflate(R.layout.fragmentlay2, container, false);

		lv = (ListView) v.findViewById(R.id.listView1);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		return v;
	}

	public interface onItemClickMethod {

		public void ClickyPosition(int positon);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int positon, long arg3) {
		// TODO Auto-generated method stub
		handler.ClickyPosition(positon);
	}

}
