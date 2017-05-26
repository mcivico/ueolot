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

import ueolot.com.ueolot.Adapters.Adapter_Cronica;
import ueolot.com.ueolot.Connexio.Croniques_Task;
import ueolot.com.ueolot.CronicaDetallActivity;
import ueolot.com.ueolot.Model.Cronica;
import ueolot.com.ueolot.R;


public class Fragment_Llista_Cronica extends Fragment {

    private ListView llista_croniques;
    private Adapter_Cronica adapter_croniques;
    private ProgressDialog dialog;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_llista_cronica, container, false);
        if (comprovarInternet(getContext())) {
            if (rootView != null) {
                llista_croniques = (ListView) rootView.findViewById(R.id.listViewCronica);
            }
            dialog = ProgressDialog.show(getActivity(), "", "Carregant dades...", true);
            new Croniques_Task(this).execute();
            llista_croniques.setClickable(true);
            llista_croniques.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    Cronica cronica_selecionada = adapter_croniques.getItem(position);

                    Intent i = new Intent(getContext(), CronicaDetallActivity.class);
                    i.putExtra("Cronica",cronica_selecionada);
                    startActivity(i);
                    getActivity().finish();
                }
            });


        }else{
            Snackbar.make(getView(),"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return rootView;
    }

    public void carregar_dades(ArrayList<Cronica> dades) {
        adapter_croniques = new Adapter_Cronica(getActivity(), dades);
        llista_croniques.setAdapter(adapter_croniques);
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
