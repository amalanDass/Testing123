package com.example.sampleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tab extends Activity implements OnClickListener {
	TabHost th; 
	TextView tv_tab1; long start,stop,results = 0;
		@Override
		protected void onCreate(Bundle savedInstanceState){
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tab);
		
			Button add = (Button)findViewById(R.id.add);
			Button startWatch = (Button)findViewById(R.id.startWatch);
			Button stopWatch = (Button)findViewById(R.id.stopWatch);
			tv_tab1 =(TextView)findViewById(R.id.tv_tab1);
			
			
			th = (TabHost)findViewById(R.id.tabhost);
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
						TextView tv = new TextView(Tab.this);
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
