package ueolot.com.ueolot.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ueolot.com.ueolot.Adapters.Adapter_FutbolBase;
import ueolot.com.ueolot.Connexio.FutbolBase_Task;
import ueolot.com.ueolot.FutbolBaseDetallActivity;
import ueolot.com.ueolot.Model.CronicaFB;
import ueolot.com.ueolot.R;


public class Fragment_Llista_FutbolBase extends Fragment {

    private ListView llista_futbolBase;
    private Adapter_FutbolBase adapter_futbolBase;
    private ProgressDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_llista_futbolbase, container, false);
        if (comprovarInternet(getContext())) {
            if (rootView != null) {
                llista_futbolBase = (ListView) rootView.findViewById(R.id.listViewFutbolBase);
            }
            dialog = ProgressDialog.show(getActivity(), "", "Carregant dades...", true);
            new FutbolBase_Task(this).execute();
            llista_futbolBase.setClickable(true);
            llista_futbolBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    CronicaFB cronica_selecionada = (CronicaFB) adapter_futbolBase.getItem(position);

                    Intent i = new Intent(getContext(), FutbolBaseDetallActivity.class);
                    i.putExtra("CronicaFB",cronica_selecionada);
                    startActivity(i);
                    getActivity().finish();

                }
            });


        }else{
            Snackbar.make(rootView,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return rootView;
    }



    public void carregar_dades(ArrayList<CronicaFB> dades) {
        adapter_futbolBase = new Adapter_FutbolBase(getActivity(), dades);
        llista_futbolBase.setAdapter(adapter_futbolBase);
        hideProgressDialog();
    }

    public void hideProgressDialog(){
        if(dialog != null)
            dialog.hide();
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
