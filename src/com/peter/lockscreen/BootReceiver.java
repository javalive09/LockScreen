package com.peter.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 启动监听服务
		Intent in = new Intent(context,LockService.class);
		context.startService(in);
	}

}