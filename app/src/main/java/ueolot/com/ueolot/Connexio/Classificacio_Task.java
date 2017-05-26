package ueolot.com.ueolot.Connexio;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import ueolot.com.ueolot.Fragments.Fragment_Clasificacio;
import ueolot.com.ueolot.helper.UEOlot_helper;

/**
 * Created by m_civico on 15/12/2016.
 */

public class Classificacio_Task extends AsyncTask<String, Void, Integer> {

    public InputStream contingut = null;
    public Classificacio_Response resp_classificacio;
    public Fragment_Clasificacio fragment;

    public Classificacio_Task(Fragment_Clasificacio fragment){
        this.fragment = fragment;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        HttpURLConnection c = null;
        int status = -1;
        try{
            URL u = new URL("http://www.ueolot.com/app/webservices/classificacio.php");
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
                resp_classificacio = new Gson().fromJson(json,Classificacio_Response.class);
            }

        }catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    protected void onPostExecute(Integer result){
        if(result != 200){
            this.fragment.hideProgressDialog();
            UEOlot_helper.showInternetError(this.fragment.getActivity());
            return;
        }
        this.fragment.carregar_dades(resp_classificacio.classificacio.positions);
    }
}
