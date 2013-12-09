package com.example.sampleactionbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpenHelper {

	private static final String DATABASE = "mydb";
	private static final String TABLE = "myFIRSTTable";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_ROW_ID = "_id";
	public static final String TABLE_ROW_NAME = "_name";
	public static final String TABLE_ROW_ADDRESS = "_address";

	private DBHelperClass dbhelper;
	private SQLiteDatabase sqldb;
	private final Context ctx;
	String results="";
	private static class DBHelperClass extends SQLiteOpenHelper {

		public DBHelperClass(Context context) {
			super(context, DATABASE, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("DATABASE CRATES...");
			db.execSQL( " CREATE TABLE "+ TABLE + " ("
					+TABLE_ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+TABLE_ROW_NAME+" TEXT NOT NULL, "
					+TABLE_ROW_ADDRESS+" TEXT NOT NULL );"
					);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			System.out.println("DATABASE ALREADY CREATED");
			db.execSQL("DROP TABLE IF EXISTS "+TABLE);
		}

	}

	public SQLOpenHelper(Context c) {
		ctx = c;
	}
	public SQLOpenHelper open() throws SQLException{
		dbhelper = new DBHelperClass(ctx);
		sqldb =dbhelper.getWritableDatabase();
		return this;
	}
	public void close(){
		sqldb.close();
	}
	public void setContentEntry(String name, String address) throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(TABLE_ROW_NAME, name);
		cv.put(TABLE_ROW_ADDRESS, address);
		
		sqldb.insert(TABLE, null, cv);
	}
	public String getData() {
		// TODO Auto-generated method stub
		
		String[] colums = new String[]{TABLE_ROW_ID,TABLE_ROW_NAME,TABLE_ROW_ADDRESS};
		
		Cursor c = sqldb.query(TABLE, colums, null, null, null, null, null);
		
		int iRowid = c.getColumnIndex(TABLE_ROW_ID);           // First row row_id
		int iRowName = c.getColumnIndex(TABLE_ROW_NAME);       // Second row row_name
		int iRowAddress = c.getColumnIndex(TABLE_ROW_ADDRESS); // Third row row_address
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			results = results + c.getString(iRowid)+" "+c.getString(iRowName)+" "+ c.getString(iRowAddress)+"\n";
		}
		return results;
	}
	public String getName(long row_id) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[]{TABLE_ROW_ID,TABLE_ROW_NAME,TABLE_ROW_ADDRESS};
		
		Cursor c = sqldb.query(TABLE, columns, TABLE_ROW_ID+"="+row_id, null, null, null, null);
		
		if(c!=null){
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		
		return null;
	}
	public String getAddress(long row_id) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[]{TABLE_ROW_ID,TABLE_ROW_NAME,TABLE_ROW_ADDRESS};
		
		Cursor c = sqldb.query(TABLE, columns, TABLE_ROW_ID+"="+row_id, null, null, null, null);
		
		if(c!=null){
			c.moveToFirst();
			String address = c.getString(2);
			return address;
		}
		return null;
	}
	public void updateEntry(long urow_id, String uname, String uaddress) throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(TABLE_ROW_ID, urow_id);
		cv.put(TABLE_ROW_NAME, uname);
		cv.put(TABLE_ROW_ADDRESS, uaddress);
		
		sqldb.update(TABLE, cv, TABLE_ROW_ID+"="+urow_id, null);
	}
	public void deleteEntry(long urow_id) throws SQLException{
		// TODO Auto-generated method stub
		sqldb.delete(TABLE, TABLE_ROW_ID+"="+urow_id, null);
		
	}
}
