package ueolot.com.ueolot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ueolot.com.ueolot.service.NotificacionesService;


public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
			Log.d("UEOLOT", "Boot Completed, Start Service");
			context.startService(new Intent(context, NotificacionesService.class));
		}
		
	}

}
