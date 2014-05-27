package com.lockscreen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private WindowManager  mWindowManager = null;
	private boolean mUpdateTime = true;
	private View mView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        Window win = getWindow();
        mWindowManager = getWindowManager();  
        WindowManager.LayoutParams params = win.getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
        mView = getLayoutInflater().inflate(R.layout.activity_main, null);
        final TextView mTimeView = (TextView) mView.findViewById(R.id.time);
        final TextView mDateView = (TextView) mView.findViewById(R.id.date);
        
        new Thread(new Runnable() {

        	SimpleDateFormat hms = new SimpleDateFormat("HH:mm", Locale.getDefault());
        	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			
			@Override
			public void run() {
				while(mUpdateTime) {
					final String time = hms.format(new Date());
					final String date = ymd.format(new Date());
					
	                runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mTimeView.setText(time);
							mDateView.setText(date);
						}
	                });
	                
	                try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
        	
        }).start();
        
        addView();
        
        startService(new Intent(MainActivity.this, LockService.class));
    }

    private void addView() {
    	LayoutParams params = new LayoutParams();  
        params.width = LayoutParams.MATCH_PARENT;  
        params.height = LayoutParams.MATCH_PARENT;  
        params.type = LayoutParams.TYPE_SYSTEM_ERROR;  
        params.flags = 1280;  
        params.format=PixelFormat.RGBA_8888;
        mWindowManager.addView(mView, params); 
    }

    public void finish() {
    	mUpdateTime = false;
    	mWindowManager.removeView(mView);
    	super.finish();
    }
    
    
    
}
