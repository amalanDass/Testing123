package com.example.sampleactionbar;

import java.lang.reflect.Field;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewConfiguration;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mcw.utility.MainFragmentAdapter;

public class MainActivity extends BaseActivity implements ActionBar.TabListener {

	ActionBar bar;
	private MainFragmentAdapter adapter;
	
	private ViewPager mViewPager;
	
	private static MainActivity main;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		main = this;
		bar = getSupportActionBar();
	
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        bar.setDisplayUseLogoEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(R.drawable.icon_ic_mcw);
//        bar.setLogo(R.drawable.ic_logo_wide);
        bar.setTitle("MYCITYWAY");
        
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.nav_bg));

        bar.addTab(bar.newTab().setText("DASHBOARD").setTabListener(this));
        bar.addTab(bar.newTab().setText("CITY GUIDE").setTabListener(this));
        bar.addTab(bar.newTab().setText("TRENDING").setTabListener(this));
        bar.addTab(bar.newTab().setText("MY STASH").setTabListener(this));
	
        
    	mViewPager = (ViewPager) findViewById(R.id.pager);

		adapter = new MainFragmentAdapter(getSupportFragmentManager(),
				MainActivity.this, bar);
		mViewPager.setOffscreenPageLimit(4);

		mViewPager.setAdapter(adapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				try {
					bar.setSelectedNavigationItem(arg0);
				} catch (Exception e) {
					e.printStackTrace();
					bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
					bar.setSelectedNavigationItem(arg0);
				}

				switch (arg0) {
				case 0:
					setTile("MYCITYWAY");
					break;
				case 1:
					setTile("CITY GUIDE");
					break;
				case 2:
					setTile("TRENDING");
					break;
				case 3:
					setTile("MY STASH");
					//forceRefreshMeTabNow();

				default:
					break;
				}

				// Global.citAbriviation =
				// MCWPreferance.getString("city_abbreviation", "");
				// currentcity.setTitle("Current city: "+Global.citAbriviation);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// dont need
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// dont need this

			}
		});
        
	}
	
	 MenuItem currentcity;
     
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
        MenuInflater inflater = getSupportMenuInflater();
        
        inflater.inflate(R.menu.main_menu, menu);
        
        currentcity = menu.findItem(R.id.action_location);
        //Global.citAbriviation = MCWPreferance.getString("city_abbreviation", "");
        currentcity.setTitle("Current city:");//+Global.citAbriviation
        
        try {
            ViewConfiguration config = ViewConfiguration.get(MainActivity.this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
           // Utils.log("before equal null");
            if (menuKeyField != null) {
         
            	//Utils.log("not equal null");
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return true;
    }

	 public void help(MenuItem item) {
         Intent i = new Intent(MainActivity.this, NewviewActivity.class);
//        i.putExtra("heading", "Help");
//         i.putExtra("url", "file:///android_asset/help.html");
         startActivity(i);
     }
    
     public void refresh(MenuItem item) {
    	 Intent i=new Intent();
		 i.setClass(getApplicationContext(), AnimationClass.class);
		 startActivity(i);
    	 //showToast("Refresh");
     	/*Intent intent = new Intent(Utils.FORCE_REFRESH_INTENT);
			LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);*/
     }
     
     public void search(MenuItem item) {
//         showToast("Search");
    	 Intent i=new Intent();
		 i.setClass(getApplicationContext(), EditTExt.class);
		 startActivity(i);
     	/*Intent i=new Intent();
			i.setClass(getApplicationContext(), SearchListingPage.class);
			Bundle b=new Bundle();
			b.putInt("categoryType", 1);
			b.putString("key", "");
			i.putExtras(b);
			startActivity(i);*/
			
     }
     
     public void location(MenuItem item) {
         /*Intent i=new Intent();
			i.setClass(getApplicationContext(), Citychange.class);
			i.putExtra("dontAskIfLivesHere", true);
			startActivityForResult(i, 1001);	*/
			Intent i = new Intent(MainActivity.this, MapVersionOne_ExtendsMapActivity.class);
			i.putExtra("title", "Map Activity" );
            i.putExtra("address", "Amalan Home" );
        	i.putExtra("lat", "40.726446" );
        	i.putExtra("lag", "-74.008255" );
        	i.putExtra( "n_id", "100" );
	        startActivity(i);
    	 
     }
     public void animationsurface(MenuItem item) {
    	 Intent i=new Intent();
		 i.setClass(getApplicationContext(), SurfaceViewExample.class);
		 startActivity(i);
     }
     public void notifications(MenuItem item) {
//         showToast("Notifications");
     	startActivity(new Intent(MainActivity.this, CameraPhoto.class));
     }
     
     public void settings(MenuItem item) {
         
         Intent i=new Intent();
		 i.setClass(getApplicationContext(), SampleListClass.class);
		 startActivity(i);
    }
     @Override
 	public void onTabSelected(Tab tab, FragmentTransaction ft) {
 		if (mViewPager != null) {
 			mViewPager.setCurrentItem(tab.getPosition());
 			if (tab.getPosition() == 3)
 				forceRefreshMeTabNow();
 		}
 	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}	
	public static void forceRefreshMeTab() {
		if (main != null)
			main.forceRefreshMeTabNow();
	}

	private void forceRefreshMeTabNow() {
		adapter.notifyDataSetChanged();
		mViewPager.setCurrentItem(3);
		bar.setSelectedNavigationItem(3);
	}

	public void onResume() {
		super.onResume();

		if (mViewPager.getCurrentItem() == 3) {
			forceRefreshMeTabNow();
		}
	}
	
	
	public void setTile(String tit) {
		bar.setTitle(tit);
	}
}
