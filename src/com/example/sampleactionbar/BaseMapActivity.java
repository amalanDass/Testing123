package com.example.sampleactionbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sampleactionbar.R;
import com.mcw.utility.Log;
import com.mcw.utility.Typefaces;
import com.example.usefullclass.Utils;
import com.slidingmenu.lib.app.SlidingMapActivity;

public class BaseMapActivity extends SlidingMapActivity{
	 
		private Typeface tf;
		
		protected String token="",userid="",deviceId="";
		protected SharedPreferences MCWPreferanceMain;
		
		protected ProgressDialog progressDialog;

		public BaseMapActivity(int titleRes) {

		}	
		//private LocalyticsSession localyticsSession;
		
		public BaseMapActivity() {
			super();
		}
		
		
		 @Override
		  protected void onResume()
		  {
			 
//			 ((MainApp)getApplication()).startSyncTaskIfNeeded();
			 
			refreshUserData();
		    System.gc();
		    
		    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(networkStateReceiver, filter);
		    
		    super.onResume();
		    
//		    this.localyticsSession.open();
		  }
		  
		 
		 BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					boolean connectivity = Utils.testNetwork(BaseMapActivity.this);
					Utils.log("onReceive connectivity:" + connectivity);
					if (!connectivity) {
						onNetworkDisconnected();
					} else {
						onNetworkConnected();
					}

				}
			};
			
		public void onNetworkDisconnected(){
			//to implement on subactivities
		}
		
		public void onNetworkConnected(){
			//to implement on subactivities
		}
			
			
			
		  @Override
		  protected void onPause()
		  { 
//		    this.localyticsSession.close();
//		    this.localyticsSession.upload();
			    
			  unregisterReceiver(networkStateReceiver);
			  
		    super.onPause();
		    System.gc();
		  }
		  //------------------------Google Analytics-----------------------------------
		  @Override
		  public void onStart() {
		    super.onStart();
		  }

		  @Override
		  public void onStop() {
		    super.onStop();
		  }
		//------------------------Google Analytics-----------------------------------
		  
		  @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);		    
			refreshUserData();
		} 
		  public void logEvent(String event){
			  //this.localyticsSession.tagEvent(event);
			}
		  public void refreshUserData() {
			  
			   MCWPreferanceMain= BaseMapActivity.this.getSharedPreferences("MCW",MODE_PRIVATE);
				token=MCWPreferanceMain.getString("token","");
				userid=MCWPreferanceMain.getString("userid","");
				deviceId=MCWPreferanceMain.getString("DeviceID", "");
		}

		@Override
		  public void setContentView(int layoutResID)
		  {
		    ViewGroup mainView = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID, null);
		    tf=Typefaces.get(getApplication(), Utils.fontFlie);
			applyFonts(mainView);		
			setContentView(mainView);
		  } 
		  @Override
		  public void setContentView(View view)
		  {
		    super.setContentView(view);
		    tf=Typefaces.get(getApplication(), Utils.fontFlie);
			 applyFonts(view);
			
		    m_contentView = (ViewGroup)view;
		  }
		  
		  @Override
		  public void setContentView(View view, LayoutParams params)
		  {
		    super.setContentView(view, params);
		    tf=Typefaces.get(getApplication(), Utils.fontFlie);
			 applyFonts(view);
			
		    m_contentView = (ViewGroup)view;
		  }
		  
		  @Override
		  protected void onDestroy()
		  {
			  Log.d("ClearView", "onDestroy");
			    
		    // Fixes android memory  issue 8488 :
		    // http://code.google.com/p/android/issues/detail?id=8488
		    nullViewDrawablesRecursive(m_contentView);
		    
		    m_contentView = null;
		    System.gc();
		    
		    super.onDestroy();
		    
		  }
		  
		  
		  public void initList(){
				
			setContentView(R.layout.error_page);	
					
			  ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  if ( 	  (     
					  	 connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!=null 
					  && connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()
					  
					  ) 
					  
					  || 
					  
					  	(
					  	    connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!=null 
					  		&& connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
					  	)
					  	
					   || 
					  
					  	(
					  	    connManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX)!=null 
					  		&& connManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnected()
					  	)
					  
					  )
				{
					setupView();
				}else{
					
					showRetryDialog();
				}
		  }
		  
		  
		  /**
		   * setUpListView to init List in the page
		   */
		  public void setupView(){
			  
		  }
		  
		  /**
		   * To show retry cancel dialog
		   */ 
		  
		public void  showRetryDialog(){
			
			  AlertDialog.Builder alert=new AlertDialog.Builder(BaseMapActivity.this);
				alert.setTitle("Connect to the internet to view this section.");
				alert.setPositiveButton("Retry", new  DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						initList();
					}
				});

				alert.setNegativeButton("Cancel",  new  DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

				alert.show();
		  }
		  
		public String colorString="red";
		
		/**
		 * setColorScheme list text
		 * @param categoryType - int
		 */
	/*	public void setColorScheme(int categoryType) {
			colorString	=Global.setColorScheme(categoryType);
			if(categoryType>0&&categoryType<=9){
				colorString=g.ListPageColorScheme[categoryType-1];
			}else{
				categoryType=1;
				colorString=g.ListPageColorScheme[categoryType-1];
			}
		}*/
		
		
		  @Override
		public void finish() {
			   
			 // overridePendingTransition(0, android.R.anim.fade_out);
			  
			  super.finish();
		}
		  
		  /**
		   *    nullViewDrawablesRecursive
		   * 
		   *     <p>This method will be called recursively to delete all inner view 
		   *     in the context of our activity when it is destroied
		   *     </p> 
		   * @param view - to be cleaned
		   * 
		   * 
		   */
		  private void nullViewDrawablesRecursive(View view)
		  {
			if(view != null)
		    {
		      try
		      {
		        ViewGroup viewGroup = (ViewGroup)view;
		        
		        int childCount = viewGroup.getChildCount();
		        for(int index = 0; index < childCount; index++)
		        {
		          View child = viewGroup.getChildAt(index);
		          nullViewDrawablesRecursive(child);
		        }
		      }
		      catch(Exception e)
		      {          
		      }
		      
		      nullViewDrawable(view);
		    }    
		  }

		  private void nullViewDrawable(View view)
		  {
		    try
		    {
		      view.setBackgroundDrawable(null);
		      
		    }
		    catch(Exception e)
		    {          
		    }
		    
		    try
		    {
		      ImageView imageView = (ImageView)view;
		      imageView.setImageDrawable(null);
		      imageView.setBackgroundDrawable(null);
		    }
		    catch(Exception e)
		    {          
		    }
		    try{
		    view=null;	
		    }catch (Exception e) {
			}
		  }

		  // The top level content view.
		  private ViewGroup m_contentView = null;

		  
		  @Override
		  public void onLowMemory() {
			 // Utils.log("DivumView", "onLowMemory");
			  System.gc();
			//  setRequestedOrientation(getResources().getConfiguration().orientation);
		  	super.onLowMemory();
		  }
		
			void applyFonts(final View v)
			  {
			      try {
			          if (v instanceof ViewGroup) {
			              ViewGroup vg = (ViewGroup) v;
			              for (int i = 0; i < vg.getChildCount(); i++) {
			                  View child = vg.getChildAt(i);
			                  applyFonts(child);
			              }
			          } else if (v instanceof TextView) {
			              ((TextView)v).setTypeface(tf);
			          }
			      } catch (Exception e) {
			          e.printStackTrace();
			          // ignore
			      }
			  }
			
			
			public void ProgressShow(String msg){
				
				try{
				progressDialog = ProgressDialog.show(this,"", msg+"...");
//				progressDialog.setCancelable(true);
//				progressDialog.setOnCancelListener(new OnCancelListener(){
	//
//					@Override
//					public void onCancel(DialogInterface arg0) {
//						try{
//						progressDialog.dismiss();
//						finish();
//						}catch (Exception e1) {
//						}
	//
//					}});
				}catch(Exception e){}
			}
			
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


			public void ShowAlert(String msg){
				
				AlertDialog.Builder alert=new AlertDialog.Builder(BaseMapActivity.this);
				alert.setTitle(msg);
				alert.setOnCancelListener(new OnCancelListener(){
					@Override
					public void onCancel(DialogInterface dialog) {
						try{
						dialog.dismiss();
						finish();
						}catch(Exception e){e.printStackTrace();}
					}
				});
				
				alert.setPositiveButton("Ok", new  DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try{
						dialog.dismiss();
						finish();
						}catch(Exception e){e.printStackTrace();}
					}
				});
				alert.show();
			}
			
			public void ShowToast(String msg){
				Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
			}

			public void ShowErrorLogin(){
				ShowToast("Please login to use this feature.");
			}

			@Override
			protected boolean isRouteDisplayed() {
				return false;
			}
			
			
	}

