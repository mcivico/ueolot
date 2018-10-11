package ueolot.com.ueolot.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import ueolot.com.ueolot.Adapters.Adapter_Noticia_PrimerEquip;
import ueolot.com.ueolot.Connexio.Noticies_1E_Task;
import ueolot.com.ueolot.Model.Noticia;
import ueolot.com.ueolot.NoticiesDetallActivity;
import ueolot.com.ueolot.R;

import static android.os.Build.VERSION_CODES.M;

public class Fragment_llista_noticies_primerEquip extends Fragment{

    private ListView llista_noticies;
    private Adapter_Noticia_PrimerEquip adapter;
    private ProgressDialog dialog;


    @RequiresApi(api = M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.fragment_llista_noticies_primer_equip,container,false);
        if(comprovarInternet(getContext())){
            llista_noticies = (ListView) rootview.findViewById(R.id.listViewNoticies1E);
            dialog = ProgressDialog.show(getActivity(),"","Carregant dades...", true);

            new Noticies_1E_Task(this).execute();
            llista_noticies.setClickable(true);
            llista_noticies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        final View view, int position, long id) {
                    Noticia noticia_seleccionada = (Noticia) adapter
                            .getItem(position);
                    Intent i = new Intent(getContext(), NoticiesDetallActivity.class);
                    i.putExtra("Noticia",noticia_seleccionada);
                    startActivity(i);
                    getActivity().finish();

                }
            });
        }else{
            Snackbar.make(rootview,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return rootview;
    }

    public void carregar_dades(ArrayList<Noticia> dades) {
        adapter = new Adapter_Noticia_PrimerEquip(getActivity(), dades);
        llista_noticies.setAdapter(adapter);
        this.hideProgressDialog();
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
