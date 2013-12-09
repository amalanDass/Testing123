package com.mcw.utility;

import com.actionbarsherlock.app.ActionBar;
import com.example.sampleactionbar.CityGuide;
import com.example.sampleactionbar.MainActivity;
import com.example.usefullclass.Utils;
import com.fragments.DashboardActivity_Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

	private DashboardActivity_Fragment snapshot;
	private CityGuide cityGuide;
	//private TrendingFragment trending;
	// private MyStashFragment myStash;
	// private UserSplashFragment userSpash;
	public static boolean logedin = false;
	@Override
	public int getItemPosition(Object object) {
/*		if (object instanceof UserSplashFragment
				|| object instanceof MyStashFragment) {
			Utils.log("getItemPosition POSITION_NONE");
			return POSITION_NONE;
		} else {*/
			Utils.log("getItemPosition POSITION_UNCHANGED");
			return POSITION_UNCHANGED;
		//}
	}

	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return super.getItemId(position);
	// }

	public MainFragmentAdapter(FragmentManager manager,
			MainActivity mainActivity, ActionBar bar) {
		super(manager);

		snapshot = new DashboardActivity_Fragment();
		cityGuide = new CityGuide();
		//trending = new TrendingFragment();
		// myStash= new MyStashFragment();
		// userSpash= new UserSplashFragment();

	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return snapshot;
			
		case 1:
			return cityGuide;
		/*case 2:
			return null;
		case 3:
			return null;
			*/
		/*case 1:
			return cityGuide;
		case 2:
			return trending;
		case 3:
			Utils.log(">>> fragment adatper, show login or profile??");
			if (mainActivity.getUserid().length() > 0 || logedin) {
				Utils.log(">>> show my stash");
				return new MyStashFragment();
			} else {
				Utils.log(">>> show login");
				return new UserSplashFragment();
			}*/
		default:
			return null;
		}

	}

}