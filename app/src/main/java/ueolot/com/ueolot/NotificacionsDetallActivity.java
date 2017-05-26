package ueolot.com.ueolot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import ueolot.com.ueolot.Model.Notificacio;
import ueolot.com.ueolot.helper.UEOlot_helper;

public class NotificacionsDetallActivity extends AppCompatActivity {

    TextView titol, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacions_detall);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Notificacio notificacio = (Notificacio)getIntent().getExtras().getSerializable("Notificacio");

        titol = (TextView)findViewById(R.id.txtNotifyTitol);
        content = (TextView)findViewById(R.id.txtNotifyContent);

        String title = UEOlot_helper.formatDateTime(notificacio.fecha);
        if(title.equals(""))
             title = notificacio.fecha;

        titol.setText(title);
        content.setText(notificacio.descripcion);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("Fragment","Notificacions");
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
}
