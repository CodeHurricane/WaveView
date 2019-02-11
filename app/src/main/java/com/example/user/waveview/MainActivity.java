package com.example.user.waveview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	 	final WaveView1 waveView = (WaveView1) findViewById(R.id.waveView);
	         waveView.postDelayed(new Runnable() {
				 @Override
				 public void run() {
					 waveView.startAnimation();
				 }
			 },1000);
	}
}
