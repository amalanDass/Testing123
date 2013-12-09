package com.example.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class ParserForXml extends DefaultHandler {

	InputSource is;
	String[] namelist;
	LocationManager lm;
	SharedPreferences setting;
	Location location;
	String[] names;
	String startElementname;
	double latitude;
	double longitude;
	String distance;
	//String[] gps;
	//String[] sb ;
	StringBuffer[] sb;
	public boolean start_item=false;
	// ValidateUserAsyncTask validator;
	boolean check[];
	
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	//ArrayList<HashMap<String, StringBuffer>> mylist = new ArrayList<HashMap<String, StringBuffer>>();
	
	//this the constructor where execution starts
	
	public ParserForXml(String[] namelist,String startElementby)
	{
		//Log.v("inCommonP","Yes");
		
		this.names=namelist;
		
		this.check =new boolean[names.length];	
		//this.sb=new String[names.length];
		this.sb=new StringBuffer[names.length];
		this.startElementname=startElementby;
		
		//gps=getGPS();
		setVariables();
	}
	
	
	
	
	
	
	int count=0;
	
	
	public String head_tile;
	public String head_link;
	public String head_desc;
	
	
	
	
	
	public void setVariables()
	{
				  
		for(int i=0;i<names.length;i++)
		{
			check[i]=false;
			sb[i]=new StringBuffer();
			
		}
	}
	
	
	
	
	
	
	
	

	public void startElement(String uri, String name, String qName,	Attributes atts) {
		
		
		
		
		for(int i=0;i<names.length;i++)
		{
			
			
		 if (name.equals(names[i].toString())){
			check[i]=true;
			
			}
		}
		
		if(name.equals(startElementname))
		{
			
			start_item=true;
		}
		
		
		
	}

	
	
	public void endElement(String uri, String name, String qName)	throws SAXException {


	if (name.trim().equals(startElementname))
	{
				//Log.v("end item","end of item");
				
				start_item=false;
				
				HashMap<String, String> map1 = new HashMap<String, String>();
			
				try {
					for(int i=0;i<names.length;i++)
					{
					
/*					if(names[i].equals("title")){
						
						String ss;
						
						//if(fromWhere==1){
							ss=sb[i].toString().substring(0,sb[i].indexOf(":City"));
						//}else{
							ss=sb[i].toString().substring(0,sb[i].indexOf(":Orientation"));
						//}
						map1.put(names[i],ss);
						ss=null;
						String lat=sb[i].toString().substring(sb[i].indexOf("Latitude=")+9,sb[i].indexOf(":Longitude")-1);
						
						
						
						map1.put("latitude",lat);
						String lan=sb[i].toString().substring(sb[i].indexOf("Longitude=")+10,sb[i].indexOf(":distance")-1);
						map1.put("longitude",lan);
						
						String dis=sb[i].toString().substring(sb[i].indexOf("distance=")+9);
						  final DecimalFormat myFormatter = new DecimalFormat("###.##");
						  
						  
						  
						
						 map1.put("distance", myFormatter.format(Double.parseDouble(dis)));
						
						lat=null;
						lan=null;
						
					}else{*/
						//System.out.println("no found"+ names[i]);
					map1.put(names[i],sb[i].toString());
					//}
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
				mylist.add(map1);
				
				
				
				
				
					
					
					for(int i=0;i<names.length;i++)
					{
						
						//this try catch is to catch null pointer execption arise because of empty tags in xml 
					    try {
					    	sb[i].replace(0,sb[i].length(),""); 
						  //sb[i].replace(sb[i]," ");
					        } catch (NullPointerException e) {
						    // sb[i]=" ";
						    // Log.v("End parser Place 2-- may be empty tag",e.toString() );
					         }
					 
					}
				 }		
				for(int i=0;i<names.length;i++)
				{
					try {
						if (name.equals(names[i].toString()))
						{
									
						check[i]=false;
						//Log.v(names[i],String.valueOf(check[i]));
						}
					} catch (Exception e) {
						//System.out.println(" End tag Place 3 :"+ e.toString());
						e.printStackTrace();
					}
     }
		
		
	}
				

	public void characters(char ch[], int start, int length) {
		try {
			if(!ch.equals(null))
			{
			    String chars = (new String(ch).substring(start, start + length));
			
				try {
				
					if(start_item)
					{
						   
							for(int i=0;i<names.length;i++)
													
							{
								
								
									
								
								//Log.v(names[i],String.valueOf(check[i]));
								if(check[i])
								{
									
									
									
									
										if(chars.length()!=0)
										{
										
										// System.out.println(chars);
										 sb[i].append(chars);
										 if(names[i].equals("showtime"))
											{
												sb[i].append(" ");
											}
										 
										 //sb[i]=chars;
										}
									
								}
								
							}
													
					}
					
						

			} catch (Exception e) {Log.e("NewsDroid", e.toString());}
			}//check for null
		} catch (Exception e) {
			//System.out.println("Place 4 read char :"+ e.toString());
			e.printStackTrace();
		}
	}

	public void createFeed(String url) throws SocketTimeoutException,IOException,SAXException {
		try {
			
			
			//System.out.println("****** In Cteeat Feed");
			//System.out.println(url.toString());
			//Log.v("In RssHandler","Create Feed");
			
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setValidating(false);
			SAXParser sp = spf.newSAXParser();
		//	Log.v("got sax pareser",sp.toString());
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			
			try {
				URL com = new URL(url);
				URLConnection con = com.openConnection();
				con.setConnectTimeout(20000);
				is = new InputSource(new InputStreamReader(con.getInputStream()));
				
				
			    } catch (SocketTimeoutException e) {
				throw e;}
	       // Log.v("InputSoruce",is.toString());
			xr.parse(is);
			
			//Log.v("RSSHAndler","xml parsed succefull");
			//Log.v("From Rss","Returing back to URLeditor");
		} catch (IOException ie) {
			Log.v("RssHandler1", ie.toString());
			throw ie;
		} catch (SAXException se) {
			Log.v("RssHandler2", se.toString());
			throw se;
		} catch (ParserConfigurationException e) {
			Log.v("RssHandler3", e.toString());
		}
	}

	

	/*
	public String getDist(String lat,String lng)
	{
		
		double lati =Double.valueOf(lat);
		double logi =Double.valueOf(lng);
		
		
		String isactivated =setting.getString("activate","nl");
		Log.v("isactivated",isactivated);
		if(isactivated.equals("1"))
	{
		
		Log.v("geting","getting from pref");
		latitude=Double.valueOf(setting.getString("lat",null));
		longitude=Double.valueOf(setting.getString("long",null));
		latitude=latitude/57.29577951;
		longitude=longitude/57.29577951;
		
	}
	else
	{
		  try {
		    	 
		       Log.v("LM",lm.toString()); 
			   location =lm.getLastKnownLocation("gps");
			   Log.v("latitude",String.valueOf(location.getLatitude()));
			   Log.v("longitude",String.valueOf(location.getLongitude()));
			   latitude=location.getLatitude()/57.29577951;
			   longitude=location.getLongitude()/57.29577951;
				}  catch (Exception e1) { Log.v("exception to get gps",e1.toString());
				     Log.v("NoGPS detected","We have hard coded values for lat and long");
				     latitude=40.759011/57.29577951;
				     longitude=-73.989876/57.29577951;	}

				
				
				
	 }//else

		 double d1=3963.0 * Math.acos(Math.sin(latitude) * Math.sin(lati) + Math.cos(latitude) * Math.cos(lati) * Math.cos(logi - longitude));
		 DecimalFormat df = new DecimalFormat("#.##");
	    	 String dist1 = df.format(d1)+"m";
		return dist1;
	}*/
	 
	
//	public String getDistance(String lat,String lan){
//		
//     	
//     	 float[] results= new float[3];
//     	// Double.parseDouble(string)
//     	 Location.distanceBetween( Double.parseDouble(gps[0]),  Double.parseDouble(gps[1]),  Double.parseDouble(lat),  Double.parseDouble(lan), results);
//     	 //multiply by 0.00062 to convert meters to miles
//         return String.valueOf(results[0]*0.00062);
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	 class ValidateUserAsyncTask extends AsyncTask<String, Void, String> {
//
//    	 @Override
//         protected String doInBackground(String... params) {
//         	   //Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)  	 
//         	 //Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
//         	 String[] gps=getGPS();
//         	 System.out.println("2");
//         	 float[] results= new float[3];
//         	// Double.parseDouble(string)
//         	 Location.distanceBetween( Double.parseDouble(gps[0]),  Double.parseDouble(gps[1]),  Double.parseDouble(params[0]),  Double.parseDouble(params[1]), results);
//             return String.valueOf(results[0]);
//         }
//    	
//        @Override
//        protected void onPostExecute(String result) {
//          //  super.onPostExecute(result);
//        	distance=result;
//           
//        }
//
//       
//    }
//	 private String[] getGPS()
//	 {
//		
//		 return Utils.gPSArray(ctx.getSharedPreferences(NYCwayPreferences.PREF_NAME, ctx.MODE_WORLD_READABLE));
//    } 
	
	 public ArrayList<HashMap<String, String>> getSecongMethod()
	 {
		 
		// Log.v("In getListfromCommomnParser",mylist.toString());
		 
		 return mylist;
	 }
	 
	 
}
