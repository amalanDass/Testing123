package com.example.sampleactionbar;

import java.util.Random;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetClass extends AppWidgetProvider{

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Random r  = new Random();
		int i = r.nextInt(1000);
		String s = String.valueOf(i);
		
		final int N = appWidgetIds.length;
		
		for(int i1 = 0; i1< N;i1++){
			int awId = appWidgetIds[i1];
			RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.widget_stuff);
			rv.setTextViewText(R.id.textView2, s);
			appWidgetManager.updateAppWidget(awId, rv);
		}
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		
		Toast.makeText(context, "It's deleted", Toast.LENGTH_LONG).show();
	}
}
