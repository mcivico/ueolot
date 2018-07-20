package ueolot.com.ueolot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ueolot.com.ueolot.Model.Noticia;
import ueolot.com.ueolot.helper.UEOlot_helper;

public class NoticiesDetallActivity extends AppCompatActivity {

    ImageView imatge;
    TextView titol, data, web;
    WebView webView;

    String mime = "text/html";
    String encoding = "utf-8";

    int width;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticies_detall);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Noticia noticia = (Noticia)getIntent().getExtras().getSerializable("Noticia");

        imatge = (ImageView)findViewById(R.id.imgNoticiaDetall);
        titol = (TextView)findViewById(R.id.txtTitol);
        data = (TextView)findViewById(R.id.txtDataNoticia);
        web = (TextView)findViewById(R.id.txtWeb);
        webView = (WebView)findViewById(R.id.webNoticia);

        /*if(comprovarInternet(getApplicationContext())) {
            new DescarregarImageTask(imatge).execute("http://www.ueolot.com/wp-content/uploads/" + noticia.getUrlimatge() + "-768x403.jpg");
            if(imatge.getDrawable() == null){
                new DescarregarImageTask(imatge).execute("http://www.ueolot.com/wp-content/uploads/" + noticia.getUrlimatge() + ".jpg");
            }
        }*/

        titol.setText(noticia.getTitle());
        data.setText(UEOlot_helper.formatDate(noticia.getDate()));
        web.setText("www.ueolot.com");

        Picasso.with(this).load("http://www.ueolot.com/wp-content/uploads/"+noticia.getUrlimatge()).into(imatge);
        /*if(imatge.getDrawable() == null)
            Picasso.with(this).load("http://www.ueolot.com/wp-content/uploads/"+noticia.getUrlimatge()+".jpg").into(imatge);*/

        webView.loadDataWithBaseURL(null, UEOlot_helper.getHtmlContent(noticia.getContent()),mime, encoding,null);
        webView.setBackgroundColor(0x00000000);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("Fragment","Noticies");
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
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


    /*private class DescarregarImageTask extends AsyncTask<String, Void, Bitmap> {
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
    }*/
}
