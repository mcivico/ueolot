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

import ueolot.com.ueolot.Fragments.Fragment_Llista_Noticia;
import ueolot.com.ueolot.helper.UEOlot_helper;

/**
 * Created by m_civico on 20/12/2016.
 */

public class Last_Resultado_Task extends AsyncTask<String, Void, Integer> {

    public InputStream contingut = null;
    public Last_Resultado_Response response;
    public Fragment_Llista_Noticia fragment;

    public Last_Resultado_Task(Fragment_Llista_Noticia fragment){this.fragment = fragment;}

    @Override
    protected Integer doInBackground(String... strings) {
        HttpURLConnection c = null;
        int status = -1;
        try{
            URL u = new URL("http://www.ueolot.com/app/webservices/last-resultado2.php");
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
                response = new Gson().fromJson(json, Last_Resultado_Response.class);
                String a = "";
            }
        }catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    protected void onPostExecute(Integer result) {
        if (result != 200 || response == null){
            return;
        }
        //new Noticies_Task(fragment).execute();
        this.fragment.carregar_dades2(response.resultado);

    }
}
