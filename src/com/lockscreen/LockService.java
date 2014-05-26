package com.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class LockService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
		registerReceiver(mScreenOffReceiver, mScreenOffFilter);
	}
	
	BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals("android.intent.action.SCREEN_OFF")) {
				Intent in = new Intent(context, MainActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(in);
			}
		}
	};

	@Override
	public void onDestroy() {
		unregisterReceiver(mScreenOffReceiver);
		startService(new Intent(LockService.this, LockService.class));
	}

	
	
}
