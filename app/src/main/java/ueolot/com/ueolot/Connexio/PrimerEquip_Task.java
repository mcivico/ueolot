package ueolot.com.ueolot.Connexio;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import ueolot.com.ueolot.Fragments.Fragment_Llista_Player;
import ueolot.com.ueolot.helper.UEOlot_helper;

/**
 * Created by m_civico on 15/12/2016.
 */

public class PrimerEquip_Task extends AsyncTask<String, Void, Integer> {

    public InputStream contingut = null;
    public PrimerEquip_Response resp_players;
    public Fragment_Llista_Player fragment;

    public PrimerEquip_Task(Fragment_Llista_Player fragment){
        this.fragment = fragment;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        HttpURLConnection c = null;
        int status = -1;
        try{
            URL u = new URL("http://www.ueolot.com/app/webservices/primer-equip2.php");
            c = (HttpURLConnection)u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length","0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            status = c.getResponseCode();
            contingut = c.getInputStream();
            if(status == 200){
                String json = UEOlot_helper.getStringFromInputStream(contingut);
                resp_players = new Gson().fromJson(json,PrimerEquip_Response.class);
                String a = "";
            }

        }catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    protected void onPostExecute(Integer result){
        if(result != 200){
            this.fragment.hideProgressDialog();
            UEOlot_helper.showInternetError(this.fragment.getActivity());
            return;
        }
        this.fragment.carregar_dades(resp_players.getJugadors());
    }
}
