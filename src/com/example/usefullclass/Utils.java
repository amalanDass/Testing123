package com.example.usefullclass;

import org.json.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.example.sampleactionbar.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;

public class Utils {

	public static DisplayImageOptions dafult_img_options = new DisplayImageOptions.Builder()
	.showImageForEmptyUri(R.drawable.ic_launcher)
//	.showStubImage(R.drawable.pressedback)
	.cacheInMemory()
	.cacheOnDisc()
	.bitmapConfig(Bitmap.Config.RGB_565)
//	.displayer(new RoundedBitmapDisplayer(20))
	.imageScaleType(ImageScaleType.EXACTLY)//IN_SAMPLE_INT
//	.displayer(new FadeInBitmapDisplayer(300))
	.build();
	
	public static final boolean log =true;
	
	public static final String	fontFlie	= "G-Unit.ttf"; 
	
	public static void log(String s) {
		if(log) 
		android.util.Log.v("mcw", s);
	}
	public static String getMyJsonString(JSONObject object,String tag) {
		String value="";
		try{
			value=Html.fromHtml(object.getString(tag)).toString();
		}catch (Exception e) {
			Log.e("Global", e+"");
		}
		return value+"";
	}
	
	/*Async Task*/
	public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task, P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
    
	public static boolean testNetwork(Context context) {
		boolean res = true;
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() == null|| !cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
			res = false;
		}
		return res;
	}
}
