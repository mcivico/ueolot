package ueolot.com.ueolot.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ueolot.com.ueolot.Adapters.Adapter_Notificacions;
import ueolot.com.ueolot.Connexio.Notificacions_Task;
import ueolot.com.ueolot.Model.Notificacio;
import ueolot.com.ueolot.NotificacionsDetallActivity;
import ueolot.com.ueolot.R;


/**
 * Created by mcivico on 20/12/2016.
 */

public class Fragment_Llista_Notificacions extends Fragment {

    private ListView llista_notificacions;
    private Adapter_Notificacions adapter_notificacions;
    private ProgressDialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_llista_notificacions,container,false);
        if(comprovarInternet(getContext())){
            llista_notificacions = (ListView)rootView.findViewById(R.id.llista_notificacions);
            dialog = ProgressDialog.show(getActivity(),"","Carregant dades...",true);
            new Notificacions_Task(this).execute();
            llista_notificacions.setClickable(true);
            llista_notificacions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    Notificacio notificacio_selecionada = adapter_notificacions.getItem(position);
                    Intent i = new Intent(getContext(), NotificacionsDetallActivity.class);
                    i.putExtra("Notificacio",notificacio_selecionada);
                    startActivity(i);
                    getActivity().finish();
                }
            });
        }else{
            Snackbar.make(rootView,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }

        return rootView;
    }

    public void carregar_dades(ArrayList<Notificacio> dades){
        adapter_notificacions = new Adapter_Notificacions(getActivity(),dades);
        llista_notificacions.setAdapter(adapter_notificacions);
        hideProgressDialog();
    }

    public void hideProgressDialog(){
        if(dialog != null){
            dialog.hide();
        }
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
}
