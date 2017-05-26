package ueolot.com.ueolot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import ueolot.com.ueolot.service.NotificacionesService;


public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
			if(IsConnected(context)){
				Log.d("UEOLOT", "Is Connected, Start Service");
				context.startService(new Intent(context, NotificacionesService.class));
			}
			else {
				Log.d("UEOLOT", "NO is Connected, Stop Service");
				context.stopService(new Intent(context, NotificacionesService.class));
			}
		}
		
	}
	
	private boolean IsConnected(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if(netInfo != null){
			boolean isConnected = netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE;
			if(isConnected){
				return true;
			}
		}
		return false;
	}
	

}
