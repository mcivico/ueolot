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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ueolot.com.ueolot.R;

public class Fragment_Classificacio_Femeni extends Fragment {

    private WebView webView;


    public Fragment_Classificacio_Femeni() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_classificacio_femeni,container,false);
        if (comprovarInternet(getContext())) {
            if (v != null) {
                webView = (WebView) v.findViewById(R.id.webViewClassificacioFemeni);
            }
            webView = (WebView) v.findViewById(R.id.webViewClassificacioFemeni);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://fcf.cat/classificacio/1819/futbol-11/quarta-catalana/grup-27");
        }else{
            Snackbar.make(v,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return v;
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