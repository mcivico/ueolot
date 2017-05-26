package ueolot.com.ueolot.Connexio;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import ueolot.com.ueolot.Model.Notificacio;
import ueolot.com.ueolot.helper.UEOlot_helper;
import ueolot.com.ueolot.receivers.NotificationReceiver;

/**
 * Created by mcivico on 20/12/2016.
 */

public class Last_Notificacion_Task extends AsyncTask<String, Void, Integer> {

    public InputStream contingut = null;
    public Notificacions_Response response;
    public Context context;

    public Last_Notificacion_Task(Context context){
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.d("UEOLOT", "Last_Notificacion_Task :: doInBackground");
        String pkLast = UEOlot_helper.getLastNotificacion(this.context);

        HttpURLConnection c = null;
        int status = -1;
        try{
            URL u = new URL("http://www.ueolot.com/app/webservices/last-notificaciones.php?pkLast="+pkLast);
            c = (HttpURLConnection)u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length","0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            status = c.getResponseCode();
            contingut = c.getInputStream();
            if(status == 200){
                String json = UEOlot_helper.getStringFromInputStream(contingut);
                this.response = new Gson().fromJson(json,Notificacions_Response.class);
            }

        }catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    protected void onPostExecute(Integer result){
        if(result != 200 || response == null){
            return;
        }

        if(response.notificaciones.size() > 0){
            Notificacio notificacion = response.notificaciones.get(0);
            String pkLast = UEOlot_helper.getLastNotificacion(this.context);
            if(pkLast == "" || pkLast == null){
                Log.d("UEOLOT", "Save Notificacion: " + notificacion.descripcion);
                UEOlot_helper.saveLastNotificacion(this.context, notificacion.pk);
            }else{
                Log.d("UEOLOT", "Do Notificacion: " + notificacion.descripcion);
                UEOlot_helper.saveLastNotificacion(this.context,notificacion.pk);

                int nofiticationId = 001;
                long[] pattern = {500,200,500,200};

                //TODO fer que vagi a la notificacio detall
                /*Intent viewIntent =  new Intent(this.context, MainActivity.class);
                viewIntent.putExtra("lastPk",notificacion.pk);
                viewIntent.putExtra("Notificacio",notificacion);
                //PendingIntent viewPendingIntent = PendingIntent.getActivity(this.context,0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent viewPendingIntent = PendingIntent.getActivity(this.context,0,viewIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this.context)
                        //.setSmallIcon(R.drawable.ueo)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ueo))
                        .setContentTitle("UE Olot")
                        .setContentText(response.notificaciones.size() > 1 ? response.notificaciones.size() + " notificacions noves." : notificacion.descripcion)
                        .setAutoCancel(true)
                        .setVibrate(pattern)
                        .setLights(Color.RED, 750,750)
                        .setContentIntent(viewPendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(nofiticationId,notificationBuilder.build());*/
            }
        }
    }

    private PendingIntent getDeleteIntent(String lastPk){
        Intent intent = new Intent(this.context, NotificationReceiver.class);
        intent.setAction("notification_cancelled");
        intent.putExtra("lastPk",lastPk);
        return PendingIntent.getBroadcast(this.context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
