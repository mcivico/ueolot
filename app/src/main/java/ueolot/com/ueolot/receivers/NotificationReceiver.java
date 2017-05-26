package ueolot.com.ueolot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ueolot.com.ueolot.helper.UEOlot_helper;


public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("notification_cancelled"))
        {
			String lastPk = intent.getStringExtra("lastPk");
			Log.d("UEOLOT", "NotificationReceiver :: " + lastPk);
			UEOlot_helper.saveLastNotificacion(context, lastPk);
        }
	}

}
