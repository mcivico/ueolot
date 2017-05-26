package ueolot.com.ueolot;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import ueolot.com.ueolot.Model.Player;
import ueolot.com.ueolot.helper.UEOlot_helper;

public class JugadorDetallActivity extends AppCompatActivity {

    ImageView imatge;
    TextView num, nom, posicio, neix,txt1, txt2;
    WebView webView;

    String mime = "text/html";
    String encoding = "utf-8";

    int width;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detall);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Player player = (Player)getIntent().getExtras().getSerializable("Jugador");

        imatge = (ImageView)findViewById(R.id.imgPersonaDetall);
        num = (TextView)findViewById(R.id.txtNumPersona);
        nom = (TextView)findViewById(R.id.txtNomPersonaDetall);
        posicio = (TextView)findViewById(R.id.txtPosPersonaDetall);
        neix = (TextView)findViewById(R.id.txtNeixLlocDetall);
        txt1 = (TextView)findViewById(R.id.txt3);
        txt2 = (TextView)findViewById(R.id.textView5);
        webView = (WebView)findViewById(R.id.txtDescPersonaDetall);



        display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        //Picasso.with(this).load(player.getImage()).into(imatge);
        if(comprovarInternet(this.getApplicationContext())) {

            new DescarregarImageTask(imatge).execute("http://www.ueolot.com/wp-content/uploads/"+player.getImage());
        }


        num.setText(player.getNumber());
        nom.setText(player.getTitle());
        posicio.setText(player.getPosition());
        if(player.getCity()==null){
            neix.setText(UEOlot_helper.formatDate(player.getDate()));
        }else{
            neix.setText(UEOlot_helper.formatDate(player.getDate())+ " | "+ player.getCity());
        }

        txt1.setText("Data i lloc de neixament");
        txt2.setText("Descripció");
        webView.loadDataWithBaseURL(null, UEOlot_helper.getHtmlContent(player.getContent()),mime, encoding,null);
        webView.setBackgroundColor(0x00000000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("Fragment","Jugador");
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private class DescarregarImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DescarregarImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 370, r.getDisplayMetrics());
                mIcon11 = redimensionarImagenMaximo(mIcon11,(float)width,px);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
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

        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);

        }
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
