package com.lockscreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends Activity {
	
	private WindowManager  mWindowManager;
	private View mView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, LockService.class));
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        Window win = getWindow();
        mWindowManager = getWindowManager();  
        WindowManager.LayoutParams params = win.getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
        mView = getLayoutInflater().inflate(R.layout.activity_main, null);
        addView();
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
    	mWindowManager.removeView(mView);
    	super.finish();
    }
    
    
    
}
