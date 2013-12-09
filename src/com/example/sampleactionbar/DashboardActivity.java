package com.example.sampleactionbar;

import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.usefullclass.GzipHttpParser;
import com.example.usefullclass.Utils;
import com.example.usefullclass.WidgetOrderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.slidingmenu.lib.SlidingMenu;

public class DashboardActivity extends BaseMapActivity{
	protected ProgressDialog progressDialog;
	String url;
	private TableLayout table_lay;
	private JSONArray itemArray;
	private ArrayList<WidgetOrderView> widgetsList = new ArrayList<WidgetOrderView>();
	private static LayoutInflater inflater = null;
	private Integer actualWidth = 0;
	private ImageLoader imageLoader;
	private JSONObject attractionObj;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//SLIDING MENU DRAWER----------
		try {
			setBehindContentView(R.layout.slide_menu);
		} catch (Exception e) {
		}
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.actionbar_home_width);

		try {
			setSlidingActionBarEnabled(false);
		} catch (Exception e){
		}
		//SLIDING MENU DRAWER----------
		
		
		setContentView(R.layout.dashboard);
		
		table_lay = (TableLayout) findViewById(R.id.table_lay);
		
		Display display = getWindowManager().getDefaultDisplay();
		
		actualWidth = display.getWidth();
		
		imageLoader = ImageLoader.getInstance();
		
		imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
		
		//private DashboardLoadTask dataTask=null;
		inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		loadDashboard();
		
		
		
		Button btn_back = (Button) findViewById(R.id.btn_menu);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
				// finish();
			}
		});
		
		
	}
	private DashboardLoadTask dataTask=null;
	
	public void loadDashboard() {

		//url = "http://perftest.nydn.mycitywaynow.com/nydnapi/dashboard?device_primary_id=100&city=India&latitude=11.021743&longitude=76.955398";

		url = "http://dev.mycitywaynow.com/dashboard/dashboard_speed_new_v2.php?format=json&api_key=b51d2d2e973bdb71002c3385dc573030&city=NewYork&time=&latitude=40.714406&longitude=-74.005969&city_name=NewYork&device_id=100&app_name=MyCityWay&app_version=1&user_id=&callback=venue_data&cal_date=";
		
		Utils.log("url:" + url);
		
		//stop existing running task
		if(dataTask!=null) dataTask.cancel(true);

		dataTask = new DashboardLoadTask();

		Utils.execute(dataTask);

	}
	class DashboardLoadTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			ProgressShow("Loading", false);

			Utils.log("Started Dashboard Task");

			networkError = false;
			
			super.onPreExecute();
		}
		
		boolean networkError = false;

		@Override
		protected Void doInBackground(Void... params) {
	
			if(Utils.testNetwork(DashboardActivity.this)){
				Utils.log("online call if no error");
				try {
				
					Utils.log("call url:" + url);
					
					GzipHttpParser parser = new GzipHttpParser(url);
					
					JSONObject obj = null;
					obj = parser.getJsonObject();
					
					if(obj!=null){
						itemArray = obj.getJSONObject("response").getJSONArray("widget");
					}
				}catch (Exception e) {
					e.printStackTrace();
					Utils.log("network error");
				}
			}
			
		return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			try {
				ProgressCancel();
			} catch (Exception e) {
				Utils.log("error canceling progressbar..!");
			}
			
			try {
				loadMetroView();
				table_lay.removeAllViewsInLayout();

				JSONArray array = itemArray.getJSONObject(0).names();
				int size = itemArray.getJSONObject(0).names().length();

				boolean AddLeftFlag = true;
				TableRow row = null;
				viewHolder holder = new viewHolder();
				View rowView = null;

				table_lay.removeAllViewsInLayout();

				row = new TableRow(DashboardActivity.this);
				holder = new viewHolder();

//				TextView tv1 = new TextView(DashboardActivity.this);
//				TextView tv2 = new TextView(DashboardActivity.this);
//				rowView = inflater.inflate(R.layout.tablerow, null);
//				holder.lin_left = (LinearLayout) rowView.findViewById(R.id.lin_left);
//				holder.lin_right = (LinearLayout) rowView.findViewById(R.id.lin_right);
//				holder.lin_left.addView(tv1, actualWidth / 2, 0);
//				holder.lin_right.addView(tv2, actualWidth / 2, 0);
//				row.addView(rowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//				table_lay.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

				int addedWidgetCount = 0;

				widgetsList = new ArrayList<WidgetOrderView>();

				for (int j = 0; j < size; j++) {
					if (array.getString(j).contains("local_news") && !array.getString(j).equalsIgnoreCase( "widget_community_news")) {
						continue;
					} else if (array.getString(j).contains("widget_add_more")) {
						continue;
					}

					else {
						WidgetOrderView newElement = new WidgetOrderView();
						newElement.setV(array.getString(j));

						int orderValue = 0;
						try {
							JSONObject ob = itemArray.getJSONObject(0).getJSONObject(array.getString(j));
							orderValue = Integer.parseInt(Utils.getMyJsonString(ob, "widget_order"));
						} catch (Exception e) {
							orderValue = 15;
						}

						try {
							JSONObject ob = itemArray.getJSONObject(0).getJSONObject(array.getString(j));
							newElement.setWidgetId(Utils.getMyJsonString(ob, "widget_type_id"));
						} catch (Exception e) {
							newElement.setWidgetId("0");
						}

						newElement.setWidget_order_id(orderValue);
						widgetsList.add(newElement);
					}
				}

				Collections.sort(widgetsList);

				for (int i = 0; i < widgetsList.size(); i++) {
					//Utils.log(widgetsList.get(i).getV() + " - " + widgetsList.get(i).getWidget_order_id());
					View CurrentV = selectWidget(widgetsList.get(i).getV());

					if (AddLeftFlag) {
						row = new TableRow(DashboardActivity.this);
						holder = new viewHolder();
						rowView = inflater.inflate(R.layout.tablerow, null);
						holder.lin_left = (LinearLayout) rowView.findViewById(R.id.lin_left);
						holder.lin_right = (LinearLayout) rowView.findViewById(R.id.lin_right);
					}

					if (AddLeftFlag) {
						
						Utils.log("CurrentV:" + CurrentV);
						Utils.log("holder:" + holder);
						if(CurrentV!=null){
						holder.lin_left.addView(CurrentV, actualWidth / 2, actualWidth * 2 / 5);
						AddLeftFlag = false;
						addedWidgetCount++;
						}
						
					} else {
						if(CurrentV!=null){
						holder.lin_right.addView(CurrentV, actualWidth / 2,actualWidth * 2 / 5);
						AddLeftFlag = true;
						addedWidgetCount++;
						}
					}

					if (addedWidgetCount % 2 == 0) {
						row.addView(rowView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
						table_lay.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					}
					
					/// save bitmaps for add more
					/*if(widgetsList.get(i).getWidgetId().equalsIgnoreCase("6")){//widget_mcw_perks
						AddMoreGrid.widget_mcw_perks = CurrentV;
					}else if(widgetsList.get(i).getWidgetId().equalsIgnoreCase("18")){//city_guide
						AddMoreGrid.city_guide = CurrentV;
					}else if(widgetsList.get(i).getWidgetId().equalsIgnoreCase("23")){//showbiz_news
						AddMoreGrid.showbiz_news = CurrentV;
					}else if(widgetsList.get(i).getWidgetId().equalsIgnoreCase("27")){//sports_news
						AddMoreGrid.sports_news = CurrentV;
					} */
				}

				if (addedWidgetCount % 2 == 1) {

					holder.lin_right.addView(selectWidget("widget_add_more"),
							actualWidth / 2, actualWidth * 2 / 5);
					addedWidgetCount++;

					row.addView(rowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					table_lay.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

				} else {

					row = new TableRow(DashboardActivity.this);
					holder = new viewHolder();

					rowView = inflater.inflate(R.layout.tablerow, null);
					holder.lin_left = (LinearLayout) rowView
							.findViewById(R.id.lin_left);
					holder.lin_left.addView(selectWidget("widget_add_more"),
							actualWidth / 2, actualWidth * 2 / 5);
					addedWidgetCount++;
					AddLeftFlag = false;

					row.addView(rowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					table_lay.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

				}


			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			super.onPostExecute(result);
		}

		View selectWidget(String id) {

			Utils.log("ID--> " + id);
			
			String localyticsLog=null;
			
			if (id.equals("weather")) {
				return weatherView();
			} 
			else if (id.equals("widget_dining")) {
				return cityGuideView();
			}
			else if (id.equals("widget_add_more")) {
				return addMore();
			}
			else if(id.equals("widget_attractions")){
				return getAttractionWidget(localyticsLog);
			}
		/*	if (id.equals("movies")) {
				return getMoviesView();
			}
			else if (id.equals("weather")) {
				return weatherView();
			} else if (id.equals("city_guide")) {
				return cityGuideView();
			} else if (id.equals("gasprices")) {
				return getGasView();
			} else if (id.equals("Transit")) {
				return getTransitView();
			} else if (id.equals("traffic_cam")) {
				return getTrafficCameraView();
			} else if (id.equals("deals")) {
				return Deals();
			} else if (id.equals("events")) {
				return getEventsView();
			} else if (id.equals("esblightning")) {
				return getESBView();
			} else if (id.equals("CityCalendar")) {
				return getCalenderView();
			} else if (id.equals("widget_add_more")) {
				return addMore();
			} else if (id.equals("widget_mcw_perks")) {
				return getMCWPerks();
			} 
			else if (id.equals("widget_dining")) {
				return getDiningWidget();
			}
			else if (id.equals("widget_trending")) {
				return getTreningWidget();
			}

			// ////startb2b
			else if (id.equals("widget_community_news")) {
				return getWidgetCommunityNews();
			} else if (id.equals("widget_community_calendar")) {
				return getWidgetCommunityCalendar();
			} else if (id.equals("widget_community_location")) {
				return getWidgetCommunityLocation();
			} else if (id.startsWith("widget_b2b_generic")) {
				return getWidgetB2bGeneric(id);
			} else if (id.equals("widget_twitter_channel")) {
				return getWidgetTwitter();
			} else if (id.equals("widget_facebook_channel")) {
				return getWidgetFacebook();
			}

			// ///////end b2b

			else if (id.equals("attractions")) {
				return getAttractionWidget();
			} else if (id.equals("widget_mcw_channel")) {
				return getMcwChannel();
			}
			// daily news
			else if (id.equals("local_news")) {
				return getLocalNewsView();//
			} else if (id.equals("sports_news")) {
				return getSportsNewsView();//
			}
			else if (id.equals("top_stories_news")) { //America
				return getTopNewsView(false);//
			} 
			else if (id.equals("politics_news")) {
				return getPoliticalNewsView(false);//
			} else if (id.equals("crime_news")) {
				return getCrimeNewsView(false);//
			} else if (id.equals("showbiz_news")) { 
				return getShowbizNewsView(false);
			} else if (id.equals("columnists_news")) {
				return getColumnistsNewsView(false);//
			} else if (id.equals("deals_news")) {
				return getDealNewsView(false);//
			} else if (id.equals("horoscope")) {
				return getHoroscopeView();//
			} else if (id.equals("opinion_news")) {
				return getOpinionView(false);//
			} else if (id.equals("dining_news")) {
				return getDiningNewsView(false);//
			} else if (id.equals("living_news")) {
				return getLivingView(false);//
			} else if (id.equals("photos_news")) {
				return getPhotosView();//
			} else if (id.equals("blog_news")) {
				return getBlogsView(false);//
			}*/
			return null;

		}
		private View addMore() {

			View vAdd = inflater.inflate(R.layout.tile_cityguide_new, null);

			final RelativeLayout layer_online = (RelativeLayout)vAdd.findViewById(R.id.layer_online);

		    layer_online.setVisibility(View.VISIBLE);
			

			vAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*
					if(offline){
//						showDisabledSectionOffline("Customize");
						showDisabledSectionOfflinecOSTUMIZE();
					}else{
						Intent i = new Intent();
						i.setClass(getApplicationContext(), AddMoreGrid.class);
						startActivityForResult(i, 202);
					}*/
				}
			});
			return vAdd;
		}
		
		private View cityGuideView() {
			// TODO Auto-generated method stub
			
			View vCamera = inflater.inflate(R.layout.tile_cityguide_new, null);
			
			final RelativeLayout layer_online = (RelativeLayout)vCamera.findViewById(R.id.layer_online);

			layer_online.setVisibility(View.VISIBLE);

			vCamera.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("This is for CITYGUIDE VEIEW");
					/*if(offline){
						showDisabledSectionOffline("City Guide");
					}else{
						Analytics_Track("Dashboard Screen","Dashboard_MCWCityGuide","","");
						Analytics_Track_MCW_Now("Dashboard Screen","Dashboard_MCWCityGuide","","");
						Intent i = new Intent();
						i.setClass(getApplicationContext(), CityGuide.class);
						i.putExtra("Dashboard", true);
						startActivity(i);
						finish();
					}*/
					MyApplication  ma = (MyApplication) getApplicationContext();
					
					ma.onTerminate();
				}
			});

			return vCamera;
		
		}
		
		View getAttractionWidget(final String localyticsLog){

			final ViewFlipper flipperDinner = new ViewFlipper(getApplicationContext());
			
			try {
				
				for(int i =0;i<itemArray.getJSONObject(0).getJSONObject("widget_attractions").getJSONArray("widget_array").length();i++){
				
				
					viewHolder holder=new viewHolder();
				
					View vAttractions=inflater.inflate(R.layout.tile_attractions,null);
					
					holder.txt_city=(TextView)vAttractions.findViewById(R.id.title);
					//holder.txt_city.setTypeface(tf);
					
					holder.img_weather=(ImageView)vAttractions.findViewById(R.id.img_dining);
					
				
						attractionObj=itemArray.getJSONObject(0).getJSONObject("widget_attractions").getJSONArray("widget_array").getJSONObject(i);
						holder.txt_city.setText(Utils.getMyJsonString(attractionObj, "attraction_name"));
						imageLoader.displayImage(Utils.getMyJsonString(attractionObj, "attraction_image"), holder.img_weather, Utils.dafult_img_options);
				
				
					flipperDinner.addView(vAttractions);
					
				
				}
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

			flipperDinner.setInAnimation(DashboardActivity.this,android.R.anim.fade_in);
			flipperDinner.setOutAnimation(DashboardActivity.this, android.R.anim.fade_out);
			flipperDinner.setFlipInterval(5000);
			flipperDinner.startFlipping();
			
			flipperDinner.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*if(localyticsLog!=null) logEvent(localyticsLog);
					Intent i=new Intent();
					i.setClass(getApplicationContext(),AttractionsList.class);
					startActivity(i);*/
				}
			});
			
			return flipperDinner;
		}
		
		private JSONArray weatheArray;
		private JSONObject weatherObj;
		private View weatherView() {
			// TODO Auto-generated method stub


			View weatherContainer = inflater.inflate(R.layout.tile_weather_new, null);

			final RelativeLayout layer_online = (RelativeLayout)weatherContainer.findViewById(R.id.layer_online);
				layer_online.setVisibility(View.VISIBLE);

			try {
				weatheArray = itemArray.getJSONObject(0).getJSONObject("weather").getJSONArray("widget_array");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			try {

				if (weatheArray != null) {

					viewHolder holder = new viewHolder();

					holder.txt_temp = (TextView) weatherContainer.findViewById(R.id.txt_temp);
					//holder.txt_temp.setTypeface(tf);
					holder.txt_desc = (TextView) weatherContainer.findViewById(R.id.txt_desc);
					//holder.txt_desc.setTypeface(tf);
					holder.txt_low = (TextView) weatherContainer.findViewById(R.id.txt_temp_low);
					//holder.txt_low.setTypeface(tf);
					holder.txt_high = (TextView) weatherContainer.findViewById(R.id.txt_temp_high);
					//holder.txt_high.setTypeface(tf);

					holder.img_weather = (ImageView)weatherContainer.findViewById(R.id.img_weather);
					try {
						weatherObj = weatheArray.getJSONObject(0);
						holder.txt_temp.setText(toFahrenheit(Utils.getMyJsonString(weatherObj, "temperature")) + (char) 0x00B0);
						holder.txt_high.setText("HI:" + toFahrenheit(Utils.getMyJsonString( weatherObj, "high_temp")) + (char) 0x00B0);
						holder.txt_low.setText("LO:" + toFahrenheit(Utils.getMyJsonString( weatherObj, "low_temp")) + (char) 0x00B0);
						holder.txt_desc.setText(Utils.getMyJsonString(weatherObj, "description"));
						holder.img_weather.setTag(Utils.getMyJsonString(weatherObj, "image_url"));
						imageLoader.displayImage(Utils.getMyJsonString(weatherObj, "image_url"), holder.img_weather);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
						weatherContainer.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								/*if(!offline){
									logEvent("DashboardWeather");
									Intent i = new Intent();
									Bundle b = new Bundle();
									b.putString("cityName", Global.getMyJsonString(weatherObj, "city_name"));
									b.putString("date", splitValueServer[0]);
									i.putExtras(b);
									i.setClass(getApplicationContext(),WeatherTabs.class);
									startActivity(i);

								}else{
									showDisabledSectionOffline("Weather");
								}*/

							}
						});

				}
			} catch (Exception e) {
			}
			return weatherContainer;
		
		}

		
	}
	
	class viewHolder {
		LinearLayout lin_left, lin_right;
		TextView txt_city, txt_temp, txt_desc, txt_camera, txt_camera_click;
		ImageView img_weather;
		WebView web_camera;
		ImageView img_movies;
		// TextView txt_coming;
		TextView txt_low, txt_high;
		ImageView img_gas1, img_gas2, img_gas3;
		TextView txt_price1, txt_price2, txt_price3, txt_price4;
	}
	
	
	public void loadMetroView() {
		//loading metro view here

		   RelativeLayout rel_img_news = (RelativeLayout)findViewById(R.id.rel_img_news);

		     rel_img_news.removeAllViews();
		     rel_img_news.removeAllViewsInLayout();

			//online data please..

			View mv = getLayoutInflater().inflate(R.layout.tile_metro_view, null);


			TextView txt_news_heading = (TextView)mv.findViewById(R.id.txt_news_heading);
			TextView heading_metro = (TextView)mv.findViewById(R.id.title);
			ImageView img_news_top = (ImageView)mv.findViewById(R.id.img_news_top);

			//txt_news_heading.setTypeface(ttf);
			//heading_metro.setTypeface(ttf);

			rel_img_news.addView(mv);

			try {

				JSONObject newsObj = itemArray.getJSONObject(0).getJSONObject("local_news");
				txt_news_heading.setText(newsObj.getJSONArray("widget_array").getJSONObject(0).getString("headingTitle"));

				img_news_top.setTag(newsObj.getJSONArray("widget_array").getJSONObject(0).getString("large_image"));

				DisplayImageOptions optionsBig= new DisplayImageOptions.Builder()
			    .cacheOnDisc()
			    .showImageOnFail(R.drawable.default_headlines_offline_widget)
			    .showImageForEmptyUri(R.drawable.default_headlines_offline_widget)
			    .build();

				imageLoader.displayImage(newsObj.getJSONArray("widget_array").getJSONObject(0).getString("large_image"), img_news_top, optionsBig);

			} catch (Exception e) {
				 e.printStackTrace();
			}

			rel_img_news.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					/*Intent i = new Intent();
					Bundle b = new Bundle();
					b.putStringArray("dateValueServer", dateValueServer);
					b.putStringArray("timeValueServer", timeValueServer);
					b.putString("category", "Metro View");
					
					
					b.putInt("category_id", Utils.WIDGET_METRO_VIEW);
					b.putString("url", Global.DN_METRO_VIEW);
					b.putString("completeurl", Global.DN_Metro_complete);
					
					i.putExtra("ad_type", Global.AD_LOCAL);
					i.putExtras(b);
					i.setClass(getApplicationContext(), NewsList.class);
					startActivity(i);*/	
				}
			});
		
	}
	
	
	//The followings you should cut and past to your Base Activity
	public void ProgressShow(String msg, boolean cancel){
		
		if(progressDialog==null){
		
			try{
			progressDialog = ProgressDialog.show(this,"", msg+"...");
			progressDialog.setCancelable(cancel);
				if(cancel){
				progressDialog.setOnCancelListener(new OnCancelListener(){
	
					@Override
					public void onCancel(DialogInterface arg0) {
						try{
						progressDialog.dismiss();
						finish();
						}catch (Exception e1) {
						}
	
					}});
				}
			}catch(Exception e){}
		
		}
	}
	public void ProgressCancel(){
		try{
		if(progressDialog.isShowing()) progressDialog.dismiss();
		}catch (Exception e) {}
		
		progressDialog= null;
	}
	

	//The followings you should cut and past to your Base Activity
	
	public String toFahrenheit(String attr3) {
		try {
			int tCel = Math.round((float) (Float.parseFloat(attr3) * 1.8 + 32));
			return tCel + "";
		} catch (Exception e) {
			return "";
		}
	}

	public String toCelsius(String attr3) {
		try {
			int tCel = Math.round((Float.parseFloat(attr3)));
			return tCel + "";
		} catch (Exception e) {
			return "";
		}

	}
	
}
