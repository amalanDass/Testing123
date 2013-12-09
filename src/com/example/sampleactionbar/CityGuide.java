package com.example.sampleactionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class CityGuide extends Fragment implements OnClickListener {
	TabHost th; 
	TextView tv_tab1; long start,stop,results = 0;
	
	private View mainV;
	
	public CityGuide() {
		super();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainV = inflater.inflate(R.layout.tab, container, false);
		
		Button add = (Button)mainV.findViewById(R.id.add);
		Button startWatch = (Button)mainV.findViewById(R.id.startWatch);
		Button stopWatch = (Button)mainV.findViewById(R.id.stopWatch);
		tv_tab1 =(TextView)mainV.findViewById(R.id.tv_tab1);
		
		
		th = (TabHost)mainV.findViewById(R.id.tabhost);
		th.setup();
		
		TabSpec spec = th.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("tab1");
		th.addTab(spec);
		

		spec = th.newTabSpec("tab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("tab2");
		th.addTab(spec);
		

		spec = th.newTabSpec("tab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("tab3");
		th.addTab(spec);
		
		
		add.setOnClickListener(this);
		startWatch.setOnClickListener(this);
		stopWatch.setOnClickListener(this);
		
		
		return mainV;
	}
	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.add:
				TabSpec newspec = th.newTabSpec("tab1");
				newspec.setContent(new TabHost.TabContentFactory() {
					
					@Override
					public View createTabContent(String tag) {
						// TODO Auto-generated method stub
						TextView tv = new TextView(getActivity());
						tv.setText("This is the text we added new");
						
						return tv;
					}
				});
				newspec.setIndicator("NewTab");
				th.addTab(newspec);
				break;
			case R.id.startWatch:
				start = System.currentTimeMillis();
				break;
			case R.id.stopWatch:
				stop = System.currentTimeMillis();
				
				results = stop-start;
				
				int millies = (int)results;
				int seconds = millies /1000;
				int minutes =seconds/60;
				
				millies = millies % 100;
				seconds = seconds % 60;
				
				
				if(results !=0 ){
					tv_tab1.setText("Total time = "+String.format("%d :%02d : %03d",minutes,seconds,millies ));	
				}
				break;
			}
		}
}
