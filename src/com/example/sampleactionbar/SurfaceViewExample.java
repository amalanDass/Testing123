package com.example.sampleactionbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SurfaceViewExample extends Activity implements OnTouchListener{

	Usefullsurface ufs;
	float x,y,Sx,Sy,Fx,Fy;
	Bitmap bmp,marker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ufs =  new Usefullsurface(this);
		x=0;y=0;
		Sx=0;Sy=0;
		Fx=0;Fy=0;
		ufs.setOnTouchListener(this);
	    bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	    marker = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
		setContentView(ufs);

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ufs.onpause();
		}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ufs.onresume();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_UP:
			Sx = event.getX();
			Sy = event.getY();
			break;
		case MotionEvent.ACTION_DOWN:
			Fx = event.getX();
			Fy = event.getY();
			break;
		}
		
		return true;
	}
	public class Usefullsurface extends SurfaceView implements Runnable{
		Thread newt;
		SurfaceHolder ourholder;
		boolean isrunning;
		public Usefullsurface(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			ourholder = getHolder();
		}
		public void onresume(){
			isrunning = true;
			newt = new Thread(this);
			newt.start();
		}
		public void onpause(){
			isrunning = false;
			while(true){
				try {
					newt.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			}
			newt = null;
		}

	@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isrunning){
				if(!ourholder.getSurface().isValid())
				continue;

				Canvas canvas = ourholder.lockCanvas();
				canvas.drawRGB(254, 50, 200);
				if(x != 0 && y !=0){
					canvas.drawBitmap(bmp, x- (bmp.getWidth()/2), y-(bmp.getHeight()/2), null);
				}
				if(Sx != 0 && Sy !=0){
					canvas.drawBitmap(marker, Sx- (marker.getWidth()/2), Sy-(marker.getHeight()/2), null);
				}
				if(Fx != 0 && Fy !=0){
					canvas.drawBitmap(marker, Fx- (marker.getWidth()/2), Fy-(marker.getHeight()/2), null);
				}
				ourholder.unlockCanvasAndPost(canvas);
			}
		}

	}
}
