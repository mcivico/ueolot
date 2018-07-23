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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView ueo,resultat,txtLinia,txtLocation;
    private ImageView team1, team2;
    private Adapter_Noticia adapter_noticia;
    private ProgressDialog dialog;
    private int _imageHeight = 0;
    private LinearLayout scoreLayout,spaceScore,spaceLocation, dadesPartit,layoutLocation;
    private Button btnSocis;


    @RequiresApi(api = M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.fragment_llista_noticies,container,false);
        if(comprovarInternet(getContext())){
            llista_noticies = (ListView) rootview.findViewById(R.id.listViewNoticia);
            //ueo = (TextView)rootview.findViewById(R.id.ueo);
            btnSocis = (Button) rootview.findViewById(R.id.btnPromocioSocis);
            dadesPartit = (LinearLayout)rootview.findViewById(R.id.UltimaPuntuacio);
            layoutLocation = (LinearLayout)rootview.findViewById(R.id.spaceLocation);
            resultat = (TextView)rootview.findViewById(R.id.txtRes);
            team1 = (ImageView) rootview.findViewById(R.id.webViewTeam1);
            txtLinia = (TextView)rootview.findViewById(R.id.txtRes);
            team2 =(ImageView)rootview.findViewById(R.id.webViewTeam2);
            txtLocation = (TextView)rootview.findViewById(R.id.txtLocation);
            scoreLayout = (LinearLayout)rootview.findViewById(R.id.UltimaPuntuacio);
            spaceScore = (LinearLayout)rootview.findViewById(R.id.spaceScore);
            spaceLocation = (LinearLayout)rootview.findViewById(R.id.spaceLocation);
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
            team2.setBackgroundColor(getResources().getColor(R.color.colortRosa2));
            txtLinia.setText(" - ");
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
                        dadesPartit.setVisibility(View.VISIBLE);
                        layoutLocation.setVisibility(View.VISIBLE);
                        /*ViewGroup.LayoutParams params = dadesPartit.getLayoutParams();
                        params.height = 100;
                        dadesPartit.setLayoutParams(params);
                        ViewGroup.LayoutParams params2 = layoutLocation.getLayoutParams();
                        params.height = 30;
                        layoutLocation.setLayoutParams(params2);*/

                    }else{
                        dadesPartit.setVisibility(View.GONE);
                        layoutLocation.setVisibility(View.GONE);
                    }

                    /*if (view.getId() == lw.getId()) {
                        final int currentFirstVisibleItem = lw.getFirstVisiblePosition();
                       if ((currentFirstVisibleItem < lw.getLastVisiblePosition())&& pos != 0 ) {
                           dadesPartit.setVisibility(View.GONE);
                           layoutLocation.setVisibility(View.GONE);
                           ViewGroup.LayoutParams params = dadesPartit.getLayoutParams();
                           params.height = 0;
                           dadesPartit.setLayoutParams(params);
                           ViewGroup.LayoutParams params2 = layoutLocation.getLayoutParams();
                           params.height = 0;
                           layoutLocation.setLayoutParams(params2);
                       }
                    }*/
                }
            });
            btnSocis.setOnClickListener(new View.OnClickListener()
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
        if(resultado != null){
            scoreLayout.setVisibility(LinearLayout.VISIBLE);
            spaceScore.setVisibility(LinearLayout.VISIBLE);
            spaceLocation.setVisibility(LinearLayout.VISIBLE);
            resultat.setText(resultado.score);
            txtLocation.setText(resultado.location);
            if(resultado.team1Descripcion.toUpperCase().contains("OLOT")){
                Picasso.with(this.getContext()).load(R.mipmap.ic_ueolot_logo).into(team1);
                Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+resultado.team2Image).into(team2);
            }else{
                Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+resultado.team1Image).into(team1);
                Picasso.with(this.getContext()).load(R.mipmap.ic_ueolot_logo).into(team2);
            }

        }
        else{
            scoreLayout.setVisibility(LinearLayout.GONE);
            spaceScore.setVisibility(LinearLayout.GONE);
            spaceLocation.setVisibility(LinearLayout.GONE);
            resultat.setText("");
            txtLocation.setText("");
            team1.setImageDrawable(null);
            team2.setImageDrawable(null);
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
