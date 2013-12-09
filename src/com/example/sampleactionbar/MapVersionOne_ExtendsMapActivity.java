package com.example.sampleactionbar;

import java.util.ArrayList;
import java.util.List;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.example.sampleactionbar.R;
/*
 * get list from getList() method of GetResult
 * 
 * 
 */
public class MapVersionOne_ExtendsMapActivity extends MapActivity {
	private MapView map=null;
	
	static Drawable result;
	private static MyLocationOverlay me=null;
	private static SitesOverlay sites=null;
	
	//GetResult gr;
	String placename;
	int selectedlistid;
	String lat;
	String lon;
	//ArrayList<HashMap<String, String>> mylist =new ArrayList<HashMap<String, String>>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmapview);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        
        if( displaymetrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM )
        {
        	setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        }
        
		//gr=new GetResult();
		//mylist=gr.getList();
        
		placename="Map Activity";
		lat="40.726446";
		lon="-74.008255";
	
	if(getIntent().getStringExtra("n_id")!=null)	
	selectedlistid=Integer.parseInt(getIntent().getStringExtra("n_id"));
			
					
		 /*findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	startActivity(new Intent(TwoColorMarkerMapForList.this,com.newnycway.Index.class));
	            	finish();
	            }
	        }); */
		
		
		
		map=(MapView)findViewById(R.id.map);
		
		map.getController().setCenter(getPoint(Double.valueOf(lat),Double.valueOf(lon)));
																						
		map.getController().setZoom(17);
		map.setBuiltInZoomControls(true);
		
		//map.setStreetView( true );
		
		sites=new SitesOverlay();
		map.getOverlays().add(sites);
		
		me=new MyLocationOverlay(this, map);
		map.getOverlays().add(me);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		me.enableCompass();
	}		
	
	@Override
	public void onPause() {
		super.onPause();
		
		me.disableCompass();
	}		
	
 	@Override
	protected boolean isRouteDisplayed() {
		return(false);
	}
	
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			map.setSatellite(!map.isSatellite());
			return(true);
		}
		else if (keyCode == KeyEvent.KEYCODE_Z) {
			map.displayZoomControls(true);
			return(true);
		}
		else if (keyCode == KeyEvent.KEYCODE_H) {
			//sites.toggleHeart();
			
			return(true);
		}
		
		return(super.onKeyDown(keyCode, event));
	}

	private GeoPoint getPoint(double lat, double lon) {
		return(new GeoPoint((int)(lat*1000000.0),(int)(lon*1000000.0)));
	}
		
	private class SitesOverlay extends ItemizedOverlay<CustomItem> {
		//private Drawable heart=null;
		private List<CustomItem> items=new ArrayList<CustomItem>();
		//private	PopupPanel panel=new PopupPanel(R.layout.popup);
		
		public SitesOverlay() {
			super(null);
			
			//heart=getMarker(R.drawable.marker);
			
			items.add(new CustomItem(getPoint(Double.valueOf(lat),Double.valueOf(lon))," ", placename,getMarker(R.drawable.marker)));
			
		/*	if(mylist.size()!=0)
			{
				for(int i=0;i<mylist.size();i++)
				{
					if(i!=selectedlistid)
					{
					items.add(new CustomItem(getPoint(Double.valueOf(mylist.get(i).get("latitude")),Double.valueOf(mylist.get(i).get("longitude"))),mylist.get(i).get("address"),mylist.get(i).get("Title"),getMarker(R.drawable.marker2)));
					
					}
				}
			}*/
			/*items.add(new CustomItem(getPoint(40.76866299974387,-73.98268461227417),"Lincoln Center","Home of Jazz at Lincoln Center",getMarker(R.drawable.marker2)));
			items.add(new CustomItem(getPoint(40.765136435316755,-73.97989511489868),"Carnegie Hall","Where you go with practice, practice, practice",getMarker(R.drawable.marker2)));
			items.add(new CustomItem(getPoint(40.70686417491799,-74.01572942733765),"The Downtown Club","Original home of the Heisman Trophy",getMarker(R.drawable.marker2)));
            */
			populate();
		}
		
		@Override
		protected CustomItem createItem(int i) {
			return(items.get(i));
		}
		
		@Override
		public void draw(Canvas canvas, MapView mapView,boolean shadow) {
			super.draw(canvas, mapView, shadow);
			
		}
 		
		@Override
		protected boolean onTap(int i) {
			//OverlayItem item=getItem(i);
			Toast.makeText(MapVersionOne_ExtendsMapActivity.this,items.get(i).getSnippet(),Toast.LENGTH_SHORT).show();
			
			/*
			 * 
			 * 
			GeoPoint geo=item.getPoint();
			Point pt=map.getProjection().toPixels(geo, null);
			View view=panel.getView();
			((TextView)view.findViewById(R.id.latitude)).setText(String.valueOf(geo.getLatitudeE6()/1000000.0));
			((TextView)view.findViewById(R.id.longitude)).setText(String.valueOf(geo.getLongitudeE6()/1000000.0));
			((TextView)view.findViewById(R.id.x)).setText(String.valueOf(pt.x));
			((TextView)view.findViewById(R.id.y)).setText(String.valueOf(pt.y));
			
			panel.show(pt.y*2>map.getHeight());*/
			
			return(true);
		}
		
		@Override
		public int size() {
			return(items.size());
		}
		
		/*void toggleHeart() {
			CustomItem focus=getFocus();
			
			if (focus!=null) {
				focus.toggleHeart();
			}
			
			map.invalidate();
		}*/
		
		private Drawable getMarker(int resource) {
			Drawable marker=getResources().getDrawable(resource);
			
			marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());
			boundCenterBottom(marker);	

			return(marker);
		}
	}
	
	/*class PopupPanel {
		View popup;
		boolean isVisible=false;
		
		PopupPanel(int layout) {
			ViewGroup parent=(ViewGroup)map.getParent();

			popup=getLayoutInflater().inflate(layout, parent, false);
									
			popup.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					hide();
				}
			});
		}
		
		View getView() {
			return(popup);
		}
		
		void show(boolean alignTop) {
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT
			);
			
			if (alignTop) {
				lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				lp.setMargins(0, 20, 0, 0);
			}
			else {
				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.setMargins(0, 0, 0, 60);
			}
			
			hide();
			
			((ViewGroup)map.getParent()).addView(popup, lp);
			isVisible=true;
		}
		
		void hide() {
			if (isVisible) {
				isVisible=false;
				((ViewGroup)popup.getParent()).removeView(popup);
			}
		}
	} */
	
	class CustomItem extends OverlayItem {
		Drawable marker=null;
		//boolean isHeart=false;
		//Drawable heart=null;
		
		CustomItem(GeoPoint pt, String name, String snippet, Drawable marker) {
			super(pt, name, snippet);
			
			this.marker=marker;
			//this.heart=heart;
		}
		
		@Override
		public Drawable getMarker(int stateBitset) {
			 result= marker;
			
			setState(result, stateBitset);
		
			return(result);
		}
		
		/*void toggleHeart() {
			isHeart=!isHeart;
		}*/
	}
}