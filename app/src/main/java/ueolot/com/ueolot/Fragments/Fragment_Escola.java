package ueolot.com.ueolot.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ueolot.com.ueolot.R;


public class Fragment_Escola extends Fragment {

    private WebView webView;
    public Fragment_Escola() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_fragment__escola, container, false);
        if (comprovarInternet(getContext())) {
            if (rootView != null) {
                webView = (WebView) rootView.findViewById(R.id.webViewEscola);
            }
            webView.loadUrl("http://www.ueolot.com/formulari-preinscripcio/");
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

}
