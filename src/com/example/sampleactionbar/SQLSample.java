package com.example.sampleactionbar;

import android.app.Activity;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SQLSample extends Activity implements OnClickListener {
	EditText editText1, editText2,etcheck;
	Button btn1, btn2,check,update,delete;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlsample);

		editText1 = (EditText) findViewById(R.id.editText1);

		editText2 = (EditText) findViewById(R.id.editText2);

		etcheck = (EditText) findViewById(R.id.etcheck);
		
		btn1 = (Button) findViewById(R.id.button1);

		btn2 = (Button) findViewById(R.id.button2);
		
		check = (Button) findViewById(R.id.check);
		update= (Button) findViewById(R.id.update);
		delete= (Button) findViewById(R.id.delete);
		tv = (TextView)findViewById(R.id.tname);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		check.setOnClickListener(this);
		update.setOnClickListener(this);
		delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.button1:
			String name = editText1.getText().toString();

			String address = editText2.getText().toString();
			
			boolean success = false;
			
			try {
				SQLOpenHelper sqlhelper = new SQLOpenHelper(SQLSample.this);
				sqlhelper.open();
				sqlhelper.setContentEntry(name, address);
				sqlhelper.close();
				success =true;
			} catch (SQLException e) {
				e.printStackTrace();
				success =false;
			}
			finally{
				if(success){
					Dialog d =new Dialog(this);
					d.setTitle("Hiiiiiiii Yah!");
					TextView tv = new TextView(this);
					tv.setText("SUCCESS!!!!!!!!");
					d.setContentView(tv);
					d.show();
				}
		 	}
			break;
		case R.id.button2:
			
			SQLOpenHelper sqlhelper = new SQLOpenHelper(SQLSample.this);
			sqlhelper.open();
			String data = sqlhelper.getData();
			sqlhelper.close();
			tv.setText(data);
			
			break;
			
		case R.id.check:
			try{
			String echeck = etcheck.getText().toString();
			SQLOpenHelper sqlhelper1 = new SQLOpenHelper(SQLSample.this);
			sqlhelper1.open();
			long row_id = Long.parseLong(echeck);
			String name1 = sqlhelper1.getName(row_id);
			String address1 = sqlhelper1.getAddress(row_id);
			tv.setText(name1 +" "+address1);
			sqlhelper1.close();
			Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(getApplicationContext(),"Sorry Not this Entry", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.update:
			try{
			String Uname = editText1.getText().toString();
			String Uaddress = editText2.getText().toString();
			String Uecheck = etcheck.getText().toString();
			long Urow_id = Long.parseLong(Uecheck);
			
			SQLOpenHelper sqlhelper2 = new SQLOpenHelper(SQLSample.this);
			sqlhelper2.open();
			sqlhelper2.updateEntry(Urow_id,Uname,Uaddress);
			sqlhelper2.close();
			Toast.makeText(getApplicationContext(),"Successfully Updated", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(getApplicationContext(),"Sorry Not for this Entry", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.delete:
			try{
				String Uecheck = etcheck.getText().toString();
				long Urow_id = Long.parseLong(Uecheck);
				SQLOpenHelper sqlhelper2 = new SQLOpenHelper(SQLSample.this);
				sqlhelper2.open();
				sqlhelper2.deleteEntry(Urow_id);
				sqlhelper2.close();
				Toast.makeText(getApplicationContext(),"Successfully Deleted", Toast.LENGTH_SHORT).show();
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(),"Sorry Not for this Entry", Toast.LENGTH_SHORT).show();
				}
			break;
			}
		
		
	};
}
