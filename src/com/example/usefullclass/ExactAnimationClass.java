package com.example.usefullclass;

import com.example.sampleactionbar.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

public class ExactAnimationClass extends View {
	Typeface text;
	Bitmap bm;
	int changingy=0;
	public ExactAnimationClass(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		bm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
		text = Typeface.createFromAsset(context.getAssets(), "G-Unit.ttf");
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		
		Paint pt = new Paint();
		pt.setARGB(50, 254, 50, 10);
		pt.setTextAlign(Align.CENTER);
		pt.setColor(Color.RED);
		pt.setTextSize(10);
		pt.setTypeface(text);
		canvas.drawText("Hai AMALAN",getWidth()/2, 200, pt);
		
		canvas.drawBitmap(bm, canvas.getWidth()/2, changingy, null);
		if(changingy <canvas.getHeight())
			changingy+=10;
		else
			changingy = 0;
		
		Rect rt = new Rect();
		rt.set(0,100 , canvas.getWidth()/2, 150);
		Paint pt1 = new Paint();
		pt1.setColor(Color.BLUE);
		canvas.drawRect(rt, pt1);
		invalidate();
	}
}
