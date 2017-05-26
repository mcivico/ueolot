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
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ueolot.com.ueolot.Adapters.Adapter_Noticia;
import ueolot.com.ueolot.Connexio.Last_Resultado_Task;
import ueolot.com.ueolot.Connexio.Noticies_Task;
import ueolot.com.ueolot.Model.Noticia;
import ueolot.com.ueolot.Model.Resultado;
import ueolot.com.ueolot.NoticiesDetallActivity;
import ueolot.com.ueolot.R;

import static android.os.Build.VERSION_CODES.M;


/**
 * Created by m_civico on 20/12/2016.
 */

public class Fragment_Llista_Noticia extends Fragment {

    private ListView llista_noticies;
    private TextView ueo,resultat,txtLinia;
    private ImageView team1, team2;
    private Adapter_Noticia adapter_noticia;
    private ProgressDialog dialog;
    private int _imageHeight = 0;

    @RequiresApi(api = M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.fragment_llista_noticies,container,false);
        if(comprovarInternet(getContext())){
            llista_noticies = (ListView) rootview.findViewById(R.id.listViewNoticia);
            //ueo = (TextView)rootview.findViewById(R.id.ueo);
            resultat = (TextView)rootview.findViewById(R.id.txtRes);
            team1 = (ImageView) rootview.findViewById(R.id.webViewTeam1);
            txtLinia = (TextView)rootview.findViewById(R.id.txtRes);
            team2 =(ImageView)rootview.findViewById(R.id.webViewTeam2);
            ViewTreeObserver viewTreeObserver = rootview.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                rootview.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                                _imageHeight = team1.getHeight();
                            }
                        });
            }

            dialog = ProgressDialog.show(getActivity(),"","Carregant dades...", true);
            new Last_Resultado_Task(this).execute();
            new Noticies_Task(this).execute();
            //ueo.setText("PARTIT");
            team1.setBackgroundColor(getResources().getColor(R.color.colortRosa2));
            txtLinia.setText(" - ");
            team2.setBackgroundColor(getResources().getColor(R.color.colortRosa2));

            llista_noticies.setClickable(true);
            llista_noticies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        final View view, int position, long id) {
                    Noticia noticia_seleccionada = (Noticia) adapter_noticia
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
        adapter_noticia = new Adapter_Noticia(getActivity(), dades);
        llista_noticies.setAdapter(adapter_noticia);
        this.hideProgressDialog();
    }

    public void carregar_dades2(Resultado resultado) {
        resultat.setText(resultado.score);
        Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+resultado.team1Image).into(team1);
        Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+resultado.team2Image).into(team2);
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
