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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ueolot.com.ueolot.Adapters.Adapter_Noticia;
import ueolot.com.ueolot.Connexio.Last_Resultado_Task;
import ueolot.com.ueolot.Connexio.Next_Resultado_Task;
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
    private TextView txtUPlocal, txtUPresultat,txtUPvisitan, txtPPlocal, txtPPvisitan, txtPPlloc;
    //private ImageView team1, team2;
    private Adapter_Noticia adapter_noticia;
    private ProgressDialog dialog;
    private int _imageHeight = 0;
    private LinearLayout  resultatsLayout;
    private Button btnSocis;
    private ImageButton ibtnSocis;


    @RequiresApi(api = M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.fragment_llista_noticies,container,false);
        if(comprovarInternet(getContext())){
            llista_noticies = (ListView) rootview.findViewById(R.id.listViewNoticia);
            //ibtnSocis = (ImageButton) rootview.findViewById(R.id.imageButton);
            resultatsLayout = (LinearLayout)rootview.findViewById(R.id.layoutResultats);

            txtUPlocal = (TextView) rootview.findViewById(R.id.txtUPlocal);
            txtUPresultat = (TextView) rootview.findViewById(R.id.txtUPresultat);
            txtUPvisitan = (TextView) rootview.findViewById(R.id.txtUPvisitant);

            txtPPlocal = (TextView) rootview.findViewById(R.id.txtPPlocal);
            txtPPvisitan = (TextView) rootview.findViewById(R.id.txtPPvisitant);
            txtPPlloc = (TextView) rootview.findViewById(R.id.txtPPdatahora);

           /* ViewTreeObserver viewTreeObserver = rootview.getViewTreeObserver();
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
            }*/

            dialog = ProgressDialog.show(getActivity(),"","Carregant dades...", true);
            new Last_Resultado_Task(this).execute();
            new Next_Resultado_Task(this).execute();
            new Noticies_Task(this).execute();
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

            llista_noticies.setOnScrollListener(new AbsListView.OnScrollListener(){
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub
                }
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // TODO Auto-generated method stub
                    final ListView lw = llista_noticies;

                    int pos = llista_noticies.getFirstVisiblePosition();
                    if(pos == 0){
                        resultatsLayout.setVisibility(View.VISIBLE);

                    }else{
                        resultatsLayout.setVisibility(View.GONE);
                    }
                }
            });
            /*ibtnSocis.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Fragment nextFrag= new Fragment_Soci();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, nextFrag,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });*/
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
        if(resultado != null){
            txtUPlocal.setText(resultado.team1Descripcion);
            txtUPvisitan.setText(resultado.team2Descripcion);
            txtUPresultat.setText(resultado.score);

        }
    }

    public void carregar_dades3(Resultado resultado) {
        if(resultado != null){
            txtPPlocal.setText(resultado.team1Descripcion);
            txtPPvisitan.setText(resultado.team2Descripcion);
            txtPPlloc.setText(resultado.location);
        }
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
