package com.example.sampleactionbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.db.Contact;
import com.example.db.DatabaseHandler;
import com.example.db.ParserForXml;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class DBSqlite_Url extends MapActivity {
	
	private ProgressDialog	dialog;
	
	private ArrayList< HashMap< String, String >> list;
	
	private Handler					mHandler;
	
	private MyAdapter					adap;
	
	private LayoutInflater				mInflater;
	
	ListView listView;
	
	private MapView map=null;
	
	private MyLocationOverlay me=null;
	
	private SitesOverlay sites=null;
	
	DatabaseHandler db;
	
	List<Contact> arraylist = new ArrayList<Contact>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		listView = (ListView)findViewById(R.id.listview);
		
		map=(MapView)findViewById(R.id.map);
		
		mHandler = new Handler();
		
		db = new DatabaseHandler(this);
	       
		try{
			arraylist = db.getAllContacts();       
		}catch(Exception e){e.printStackTrace();}
		if(arraylist!=null && !arraylist.isEmpty() && arraylist.size()>0){
			   
			System.out.println("from db");
			
	    	adap		=	null;
			
	    	adap		=	new MyAdapter( DBSqlite_Url.this, arraylist );
				
	    	listView.setAdapter( adap );
		}
		else{
			System.out.println("from url");
			getArrayFromNearBy();
		}
		
		
		(findViewById(R.id.list)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listView.setVisibility(View.VISIBLE);
				
				map.setVisibility(View.GONE);
				
					try{
						arraylist = db.getAllContacts();       
					}catch(Exception e){e.printStackTrace();}
					if(arraylist!=null && !arraylist.isEmpty() && arraylist.size()>0){
						   
						System.out.println("from db");
						
				    	adap		=	null;
						
				    	adap		=	new MyAdapter( DBSqlite_Url.this, arraylist );
							
				    	listView.setAdapter( adap );
					}
					else{
						System.out.println("from url");
						getArrayFromNearBy();
					}
			}
		});
		

		(findViewById(R.id.btn_map)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				listView.setVisibility(View.GONE);
				
				map.setVisibility(View.VISIBLE);
				
            	map.getController().setCenter(getPoint(Double.valueOf(arraylist.get( 0 ).getLat()),Double.valueOf(arraylist.get( 0 ).getLng())));
       			
            	map.getController().setZoom(17);
       		
            	map.setBuiltInZoomControls(true);
       		
            	sites=new SitesOverlay();
       		
            	map.getOverlays().add(sites);
       		
            	me=new MyLocationOverlay(DBSqlite_Url.this, map);
       		
            	map.getOverlays().add(me);
         	    
			}
		});
		
		
		
	}
	 public  void getArrayFromNearBy()
	 {
		 dialog	=	null;
	 
		 dialog	=	new ProgressDialog(this);
  	
		 dialog.setTitle( "Please Wait ..." );
 	
		 dialog.setMessage( "Loading ..." );
	
		 dialog.show();
	
		 new Thread()
		 {
			 public void run()
			 {     
				 
				 list		=	callThis("http://api.experiencela.com/ws1.0/data/nearby?lng=-118.26&lat=34.05&dist=1&category=");
				 
				 if(mHandler!=null){
				 mHandler.post( showServiceUpdate );
				 }
			 }
		 }.start();
	 }
	 
	 private ArrayList< HashMap< String, String >> callThis( String url)
	 {
    	try 
    	{    		
    		ParserForXml cp 	=	null;
    		
    		System.out.println("urls->"+url);
    		
    		String[] names		=	{ "title", "lat","lng" };
    		
    		cp		=	new ParserForXml( names, "artpiece");
    		
    		cp.createFeed( url );
    		
    		ArrayList< HashMap< String, String >> mylist	=	cp.getSecongMethod();
    		
    		return mylist; 
    		
    	} 
    	catch ( Exception e ) 
    	{
    		return new ArrayList< HashMap< String, String >>();
    	}
    }
	 
	    private Runnable showServiceUpdate = new Runnable()
	    {  
	    	public void run()
	    	{ 
	    		if( dialog != null )
	    			dialog.dismiss();
	    		
	    		if(list==null)
			 	{
			 		Toast.makeText( DBSqlite_Url.this, "no_net_connection", Toast.LENGTH_SHORT ).show();
		
			 		finish();
			 	}
	    		else if(list.size()==0)
			 	{
			 		Toast.makeText( DBSqlite_Url.this, "newNoFeed", Toast.LENGTH_SHORT ).show();
		
			 		finish();
			 	}
	    		else
	    		{
	    			setList( list ,false);
	    		}//else
	    	}
	    };
	    
	    private void setList( ArrayList< HashMap< String, String >> newlist ,boolean fromSearch)
	    {
	    	//System.out.println("XXXXXXXXXXX----"+newlist);
	    	for(int i=0;i<newlist.size();i++){
	    	db.addContact(new Contact(newlist.get(i).get("title"), newlist.get(i).get("lat"), newlist.get(i).get("lng")));
	    	}
	    	
	    	arraylist = db.getAllContacts();       
	    	
	    	
	    	adap		=	null;
			
	    	adap		=	new MyAdapter( DBSqlite_Url.this, arraylist );
				
	    	listView.setAdapter( adap );
	    	
	    }
	    
	    private class MyAdapter extends BaseAdapter 
	    {
	    	List<Contact> contacts;
	    	
	    	Context ctx;
	      
	    	public MyAdapter( Context context, List<Contact> contacts )
	    	{
	    		this.contacts		=	contacts;
	               
	    		this.ctx			=	context;

	    		// Cache the LayoutInflate to avoid asking for a new one each time.
	    		mInflater			=	LayoutInflater.from( ctx );
	    	}

	    	public boolean isEnabled( int position )
	    	{
	    		return true;
	    	}


	    	@Override
	        public int getCount()
	    	{
	    		return contacts.size();
	    	}

	    	@Override
	    	public Object getItem( int position )
	    	{
	    		return contacts.get( position );
	    	}
	        	
	    	@Override
	    	public long getItemId( int position )
	    	{
	    		return position;
	    	}

	    	@Override
	        public View getView( int position, View convertView, ViewGroup parent )
	    	{
	    		// A ViewHolder keeps references to children views to avoid unneccessary calls
	            // to findViewById() on each row.
	            
	    		ViewHolder holder;

	         
	    		// When convertView is not null, we can reuse it directly, there is no need
	            // to reinflate it. We only inflate a new View when the convertView supplied
	            // by ListView is null.
	            
	    		if( convertView == null )
	    		{
	                convertView		=	mInflater.inflate( R.layout.title_and_distance, null );
	                
	                // Creates a ViewHolder and store references to the two children views
	                // we want to bind data to.
	                
	                holder			=	new ViewHolder();	
	                
	                holder.name		=	( TextView )convertView.findViewById( R.id.one );
	                
	                holder.dis		=	( TextView )convertView.findViewById( R.id.two );
	               
	                convertView.setTag( holder );	
	    		}  
	    		else
	    		{
	    			holder		=	( ViewHolder ) convertView.getTag();
	    		} 

	    		// LinearLayout linear=(LinearLayout)convertView.findViewById(R.id.image_holder);
	           
	    		holder.name.setText( contacts.get( position ).getName());
	             
	    		//convertView.setOnClickListener( new OnItemClickListener( position, elements ));
	                   
	    		return convertView;
	    	}	

	    	class ViewHolder
	    	{
	    		TextView name;
	            
	    		TextView dis;
	        }

	    	private class OnItemClickListener implements OnClickListener
	    	{           
	    		private int mPosition;
	    		
	    		private ArrayList< HashMap< String, String >> list_;
	     
	    		OnItemClickListener( int position, ArrayList< HashMap< String, String >> list )
	    		{
	    			mPosition	= position;
	    			
	    			list_		=	list;
	    		}
	        
	    		@Override
	    		public void onClick( View arg0 )
	    		{
	    	    	/*GetResult gr		=	new GetResult();
	    	    	
	    	    	gr.setTrafficWebCamList( list_ );
	    	        
	    	    	gr.setTrafficMapForWebCamList( list_ );
	    	         
	    	    	gr.setList( list_ );
	    	         
	    			Intent i	=	new Intent( MainActivity.this, com.newnycway.trafficcam.TrafficWebView.class );
	         	
	    			i.putExtra("id", Integer.toString( mPosition ));
	         	
	    			startActivity( i );*/
	    		}               
	    	}
	    }//class
		 private GeoPoint getPoint(double lat, double lon) {
				return(new GeoPoint((int)(lat*1000000.0),(int)(lon*1000000.0)));
			}
				
		private class SitesOverlay extends ItemizedOverlay<CustomItem> {
			//private Drawable heart=null;
			private List<CustomItem> items=new ArrayList<CustomItem>();
			//private	PopupPanel panel=new PopupPanel(R.layout.popup);
			
			public SitesOverlay() {
				super(null);
				
/*				String lat=list.get( 0 ).get( "latitude" );
	             
            	String lon=list.get( 0 ).get( "longitude" );	
            	
            	String placename =list.get( 0 ).get( "title" );	*/
            	
            	//int selectedlistid = 0;
				
				//items.add(new CustomItem(getPoint(Double.valueOf(lat),Double.valueOf(lon)), String.valueOf(0), placename,getMarker(R.drawable.marker2)));
		
            	
				if(arraylist.size()!=0)
				{
					
					for(int i=0;i<arraylist.size();i++)
					{
						//if(i!=selectedlistid)
						//{
							
						items.add(new CustomItem(getPoint(Double.parseDouble(arraylist.get(i).getLat()),Double.parseDouble(arraylist.get(i).getLng())),String.valueOf(i),arraylist.get(i).getName(),getMarker(R.drawable.marker2)));
						
						//}
					}
				}
			
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
				Toast.makeText(DBSqlite_Url.this,items.get(i).getSnippet(),Toast.LENGTH_SHORT).show();
				
		
				/*int getloc=Integer.parseInt(items.get(i).getTitle());
				Intent is= new Intent(MainActivity.this,com.newnycway.trafficcam.ShowWebForMap.class);
				is.putExtra("url",list.get(getloc).get("description"));
				startActivity(is);*/
				return(true);
			}
			
			@Override
			public int size() {
				return(items.size());
			}
			
			
			
			private Drawable getMarker(int resource) {
				Drawable marker=getResources().getDrawable(resource);
				
				marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());
				boundCenterBottom(marker);	

				return(marker);
			}
		}
		
		class CustomItem extends OverlayItem {
			Drawable marker=null;
			
			
			CustomItem(GeoPoint pt, String name, String snippet, Drawable marker) {
				super(pt, name, snippet);
				
				this.marker=marker;
				//this.heart=heart;
			}
			
			@Override
			public Drawable getMarker(int stateBitset) {
				Drawable result= marker;
				
				setState(result, stateBitset);
			
				return(result);
			}
			
			/*void toggleHeart() {
				isHeart=!isHeart;
			}*/
		}
		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
	    
}
