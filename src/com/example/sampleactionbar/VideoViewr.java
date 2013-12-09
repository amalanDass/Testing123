package com.example.sampleactionbar;

import com.example.sampleactionbar.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewr extends Activity{
 
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.videoviewer);
	
	final MediaController mediaController = new MediaController(this); 
	final VideoView videoView = (VideoView) findViewById(R.id.videoView1);
	
	Button btn1 = (Button)findViewById(R.id.button1);
	Button btn2 = (Button)findViewById(R.id.button2);
	
	btn1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mediaController.setAnchorView(videoView);
			videoView.setMediaController(mediaController);
			videoView.setVideoURI(Uri.parse("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4"));
			videoView.start();
		}
	});
	btn2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.cartoon));
			videoView.setMediaController(mediaController);

			videoView.requestFocus();
			videoView.start();
			//- See more at: http://theandroid.in/how-to-play-video-file-using-videoview-in-android/#sthash.1QsGYYKB.dpuf
		}
	});
}
	
	
}
