package com.example.sampleactionbar;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class Location_Manager extends Activity implements LocationListener{
	LocationManager lm;
	String towers;
	int lat,longi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria ct = new Criteria();
		towers = lm.getBestProvider(ct, false);
		Location lc = lm.getLastKnownLocation(towers);
		
		if(lc!=null){
			
			lat = (int) lc.getLatitude();
			
			longi = (int) lc.getLongitude();
			
			System.out.println("LOCATIO0N--lati--"+lat);
			
			System.out.println("LOCATIO0N--longi--"+longi);
		}
		else
			Toast.makeText(getBaseContext(), "Could not get ur location", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onLocationChanged(Location l) {
		// TODO Auto-generated method stub
		lat = (int)(l.getLatitude() * 1E6);
		longi = (int)(l.getLongitude() * 1E6);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lm.requestLocationUpdates(towers, 500, 1, this);
	}
}
