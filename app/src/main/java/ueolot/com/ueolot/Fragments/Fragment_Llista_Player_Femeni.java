package ueolot.com.ueolot.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
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
import android.widget.ListView;

import java.util.ArrayList;

import ueolot.com.ueolot.Adapters.Adapter_Player_Femeni;
import ueolot.com.ueolot.Connexio.PrimerEquipFemeni_Task;
import ueolot.com.ueolot.Model.Player;
import ueolot.com.ueolot.R;

public class Fragment_Llista_Player_Femeni extends Fragment {

    //private  SwipeRefreshLayout swipeRefreshLayout;
    private ListView llista_persones;
    private Adapter_Player_Femeni adapter;
    private ProgressDialog dialog;
    Image imatge;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_llista_player_femeni,container,false);
        if(comprovarInternet(getContext())) {
            if(v != null){
                llista_persones = (ListView)v.findViewById(R.id.listViewPersonaFemeni);
            }
            dialog = ProgressDialog.show(getActivity(),"","Carregant dades...",true);

            new PrimerEquipFemeni_Task(this).execute();

            /*llista_persones.setClickable(true);
            llista_persones.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                    Player player_seleccionat = adapter
                            .getItem(position);

                    Intent i = new Intent(getContext(), JugadorDetallActivity.class);
                    i.putExtra("Jugador",player_seleccionat);
                    startActivity(i);
                    getActivity().finish();

                }
            });*/


        }else{
            Snackbar.make(v,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return v;
    }

    public void carregar_dades(ArrayList<Player> arrayList) {
        adapter = new Adapter_Player_Femeni(getActivity(), arrayList);
        llista_persones.setAdapter(adapter);
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
