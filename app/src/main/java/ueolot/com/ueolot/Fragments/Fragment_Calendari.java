package ueolot.com.ueolot.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ueolot.com.ueolot.Connexio.Eventos_Fecha_Task;
import ueolot.com.ueolot.Model.Evento;
import ueolot.com.ueolot.R;


public class Fragment_Calendari extends Fragment {

    private CalendarView calendar;
    private Calendar c;
    private ProgressDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_calendari,container,false);
        if(comprovarInternet(getContext())) {
           calendar = (CalendarView) rootView.findViewById(R.id.calendar);
            calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.colorFondoSetmana));
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                    Log.d("UEOLOR","onSelectedDayChange");
                    if(c != null){
                        if (c.get(Calendar.YEAR) == year
                                && c.get(Calendar.MONTH) == month
                                && c.get(Calendar.DAY_OF_MONTH) == dayOfMonth){
                            return;
                        }
                    }
                    c = Calendar.getInstance();
                    c.set(year, month, dayOfMonth);
                    String date = new SimpleDateFormat("yyyMMdd").format(c.getTime());
                    dialog = ProgressDialog.show(getActivity(),"","Carregant dades...", true);

                    new Eventos_Fecha_Task(Fragment_Calendari.this).execute(date);
                }
            });

        }else{
            Snackbar.make(rootView,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }

        return rootView;
    }
    public static boolean comprovarInternet(Context context){

        boolean connexio = false;

        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] xarxes = connect.getAllNetworkInfo();

        for(int i=0; i<xarxes.length; i++){
            if(xarxes[i].getState() == NetworkInfo.State.CONNECTED)
                connexio = true;
        }
        return connexio;
    }

    public void tractarResultado(final ArrayList<Evento> eventos) {
        hideProgressDialog();
        String message = "\n";
        if (eventos != null && eventos.size() > 0) {
            for (Evento evento : eventos) {
                message += evento.descripcion + "\n\n";
            }
        } else {
            message = "No hi ha cap esdeveniment.";
        }


        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        alert.setMessage(message);
        alert.setPositiveButton("OK", null);
        // alert.setIcon(R.drawable.logo);
        alert.show();
    }

    public void hideProgressDialog(){
        if(dialog != null)
            dialog.hide();
    }


}
