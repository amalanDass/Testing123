package com.example.sampleactionbar;

import java.lang.reflect.Field;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.view.ViewConfiguration;


public class NewviewActivity extends SherlockFragmentActivity {
	ActionBar bar;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	    setContentView(R.layout.activity_main);

	
	}
	 MenuItem currentcity;
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	  MenuInflater inflater = getSupportMenuInflater();
      
      inflater.inflate(R.menu.main_menu, menu);

      menu.findItem(R.id.action_refresh).setVisible(false);
      	  
      currentcity = menu.findItem(R.id.action_location);
      //Global.citAbriviation = MCWPreferance.getString("city_abbreviation", "");
      currentcity.setTitle("Current city:");//+Global.citAbriviation
      
      try {
          ViewConfiguration config = ViewConfiguration.get(NewviewActivity.this);
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
//    Intent i = new Intent(WebviewActivity.this, WebviewActivity.class);
//   i.putExtra("heading", "Help");
//    i.putExtra("url", "file:///android_asset/help.html");
 //   startActivity(i);
}





public void refresh(MenuItem item) {
    //showToast("Refresh");
	/*Intent intent = new Intent(Utils.FORCE_REFRESH_INTENT);
		LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);*/
}

public void search(MenuItem item) {
//    showToast("Search");
	
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
}

public void notifications(MenuItem item) {
//    showToast("Notifications");
	//startActivity(new Intent(MainActivity.this, Notifications.class));
}

public void settings(MenuItem item) {
    
   /* Intent i=new Intent();
		i.setClass(getApplicationContext(), Settings.class);
		startActivity(i);*/
		
}



}
