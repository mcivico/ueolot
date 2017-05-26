package ueolot.com.ueolot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import ueolot.com.ueolot.Fragments.Fragment_Calendari;
import ueolot.com.ueolot.Fragments.Fragment_Clasificacio;
import ueolot.com.ueolot.Fragments.Fragment_EnViu;
import ueolot.com.ueolot.Fragments.Fragment_Escola;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Cronica;
import ueolot.com.ueolot.Fragments.Fragment_Llista_FutbolBase;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Noticia;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Notificacions;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Player;
import ueolot.com.ueolot.Fragments.Fragment_Soci;
import ueolot.com.ueolot.app.Config;
import ueolot.com.ueolot.util.NotificationUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtRegId = (TextView) findViewById(R.id.textView3);
        txtMessage = (TextView) findViewById(R.id.textView4);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        Fragment fragment = null;
        if(extras != null){
            String frag = extras.getString("Fragment");
            if(frag != null){
                fragment = loadFragment(fragment,frag);
            }
        }else{
            fragment = new Fragment_Llista_Noticia();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame,fragment).addToBackStack(null).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    public Fragment loadFragment(Fragment fragment, String frag){
        if(frag.equals("Cronica")){
            fragment = new Fragment_Llista_Cronica();
        }else if(frag.equals("Noticies")){
            fragment = new Fragment_Llista_Noticia();
        }else if(frag.equals("FutbolBase")){
            fragment = new Fragment_Llista_FutbolBase();
        }else if(frag.equals("Notificacions")){
            fragment = new Fragment_Llista_Notificacions();
        }else if(frag.equals("Jugador")){
            fragment = new Fragment_Llista_Player();
        }else {
            fragment = new Fragment_Llista_Noticia();
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()==0){
            super.onBackPressed();
        }else if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }else{
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;

        if (id == R.id.nav_noticies) {
            fragment = new Fragment_Llista_Noticia();
            fragmentTransaction = true;
        } else if (id == R.id.nav_classificacio) {
            fragment = new Fragment_Clasificacio();
            fragmentTransaction = true;

        } else if (id == R.id.nav_cronica) {
            fragment = new Fragment_Llista_Cronica();
            fragmentTransaction = true;

        }else if (id == R.id.nav_calendar) {
            fragment = new Fragment_Calendari();
            fragmentTransaction = true;

        }else if (id == R.id.nav_live) {
            fragment = new Fragment_EnViu();
            fragmentTransaction = true;

        } else if (id == R.id.nav_1equip) {
            fragment = new Fragment_Llista_Player();
            fragmentTransaction = true;

        }else if (id == R.id.nav_futbase) {
            fragment = new Fragment_Llista_FutbolBase();
            fragmentTransaction = true;
        }else if (id == R.id.nav_escola) {
            fragment = new Fragment_Escola();
            fragmentTransaction = true;
        }else if (id == R.id.nav_notificacions) {
            fragment = new Fragment_Llista_Notificacions();
            fragmentTransaction = true;
        }else if (id == R.id.nav_face) {
            obrirURL("http://www.facebook.com/UEO1921");
        }else if (id == R.id.nav_twitter) {
            obrirURL("http://www.twitter.com/UEO1921");
            /*Intent intent = null;
            try{
                this.getPackageManager().getPackageInfo("com.twitter.android",0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=278490534"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }catch(Exception e){
                intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitter.com/UEO1921"));
            }
            this.startActivity(intent);*/

        }else if (id == R.id.nav_insta) {
            obrirURL("http://www.instagram.com/ueo1921/");
        }else if (id == R.id.nav_soci) {
            fragment = new Fragment_Soci();
            fragmentTransaction = true;
        }


        if(fragmentTransaction) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            item.setCheckable(true);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void obrirURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent =  new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
