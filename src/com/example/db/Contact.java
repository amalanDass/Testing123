package com.example.db;

public class Contact {
	
	//private variables
	int _id;
	String _name;
	String _lat;
	String _lng;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name, String _lat,String _lng){
		this._id = id;
		this._name = name;
		this._lat = _lat;
		this._lng = _lng;
		
	}
	
	// constructor
	public Contact(String name, String _lat,String _lng){
		this._name = name;
		this._lat = _lat;
		this._lng = _lng;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting  lat
	public String getLat(){
		return this._lat;
	}
	
	// setting  lat
	public void setLat(String lat){
		this._lat = lat;
	}
	
	// getting  lon
	public String getLng(){
		return this._lng;
	}
	
	// setting lon
	public void setLng(String lng){
		this._lng = lng;
	}
}
